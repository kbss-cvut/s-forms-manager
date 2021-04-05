package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.*;
import cz.cvut.kbss.sformsmanager.model.persisted.response.QuestionSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.model.persisted.response.RecordSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.*;
import cz.cvut.kbss.sformsmanager.persistence.dao.remote.QuestionTemplateSnapshotRemoteDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.remote.RecordSnapshotRemoteDAO;
import cz.cvut.kbss.sformsmanager.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RemoteDataProcessingOrchestratorImpl implements RemoteDataProcessingOrchestrator {

    private final RecordDAO recordDAO;
    private final RecordSnapshotRemoteDAO recordRemoteDAO;
    private final RecordSnapshotDAO recordSnapshotDAO;

    private final FormTemplateVersionDAO formTemplateVersionDAO;

    private final QuestionTemplateSnapshotDAO questionTemplateSnapshotDAO;
    private final QuestionTemplateSnapshotRemoteDAO questionsSnapshotRemoteDataDAO;
    private final FormTemplateDAO formTemplateDAO;
    private final SubmittedAnswerDAO submittedAnswerDAO;
    private final RecordVersionDAO recordVersionDAO;

    @Autowired
    public RemoteDataProcessingOrchestratorImpl(
            RecordDAO recordDAO, QuestionTemplateSnapshotDAO questionTemplateSnapshotDAO,
            RecordSnapshotRemoteDAO recordRemoteDAO,
            RecordSnapshotDAO recordSnapshotDAO,
            FormTemplateVersionDAO formTemplateVersionDAO,
            QuestionTemplateSnapshotRemoteDAO questionsSnapshotRemoteDataDAO,
            FormTemplateDAO formTemplateDAO,
            SubmittedAnswerDAO submittedAnswerDAO,
            RecordVersionDAO recordVersionDAO) {

        this.recordDAO = recordDAO;
        this.questionTemplateSnapshotDAO = questionTemplateSnapshotDAO;
        this.recordRemoteDAO = recordRemoteDAO;
        this.recordSnapshotDAO = recordSnapshotDAO;
        this.formTemplateVersionDAO = formTemplateVersionDAO;
        this.questionsSnapshotRemoteDataDAO = questionsSnapshotRemoteDataDAO;
        this.formTemplateDAO = formTemplateDAO;
        this.submittedAnswerDAO = submittedAnswerDAO;
        this.recordVersionDAO = recordVersionDAO;
    }

    @Transactional
    public void processDataSnapshotInRemoteContext(String projectName, URI contextUri) throws IOException {

        // TODO: use Optional.getOrElse and extract redundant code

        // LOAD REMOTE DATA: record snapshot
        RecordSnapshotRemoteData recordRemoteData = recordRemoteDAO.getRecordSnapshot(projectName, contextUri);
        String recordSnapshotKey = ObjectUtils.createKeyForContext(projectName, contextUri);

        // Record
        String recordKey = ObjectUtils.createKeyForContext(projectName, recordRemoteData.getRemoteRecordURI()); // TODO:  + "/" + recordRemoteData.getRecordCreateDate()
        Optional<Record> recordOpt = recordDAO.findByKey(projectName, recordKey);
        Record record;
        if (recordOpt.isPresent()) {
            record = recordOpt.get();
        } else {
            record = recordDAO.update(projectName, new Record(recordKey, new HashSet<>(), new HashSet<>(), null, recordRemoteData.getRecordCreateDate(), contextUri.toString()));
        }

        if (recordRemoteData.getQuestion() == null) {
            // initial record: no questions or answers variant

            // persist without RecordVersion and without FormTemplateVersion
            RecordSnapshot recordSnapshot = new RecordSnapshot(recordSnapshotKey, record, null, null, null, recordRemoteData.getRecordModifiedDate(), contextUri);
            recordSnapshotDAO.persist(projectName, recordSnapshot);

            // re-update record
            record.getRecordSnapshots().add(recordSnapshot);
            recordDAO.update(projectName, record);
            return;
        }

        // LOAD REMOTE DATA: question and answers snapshot
        QuestionSnapshotRemoteData qaRemoteData = questionsSnapshotRemoteDataDAO.getQuestionsAndAnswersSnapshot(projectName, contextUri, recordRemoteData.getQuestion());

        // PROCESS the questions tree
        QuestionTreeRemoteDataProcessor processor = new QuestionTreeRemoteDataProcessor(qaRemoteData);
        processor.process();

        // Answers
        Map<String, SubmittedAnswer> submittedAnswerMap = mapToSubmittedAnswersAndPersistEventually(processor.getAnsweredQuestions(), projectName);

        // FormTemplate
        String formTemplateKey = ObjectUtils.createKeyForContext(projectName, processor.getRootQuestionOrigin());
        Optional<FormTemplate> formTemplateOpt = formTemplateDAO.findByKey(projectName, formTemplateKey);

        FormTemplate formTemplate;
        if (formTemplateOpt.isPresent()) {
            formTemplate = formTemplateOpt.get();
        } else {
            formTemplate = new FormTemplate(formTemplateKey);
            formTemplate = formTemplateDAO.update(projectName, formTemplate);
        }

        // FormTemplateVersion
        String formTemplateVersionKey = ObjectUtils.createKeyForContext(projectName, processor.getAllQuestionOriginsPathsHash());
        Optional<FormTemplateVersion> formTemplateVersionOpt = formTemplateVersionDAO.findByKey(projectName, formTemplateVersionKey);

        FormTemplateVersion formTemplateVersion;
        if (formTemplateVersionOpt.isPresent()) {
            formTemplateVersion = formTemplateVersionOpt.get();

            // add new answers to question template snapshots and update them directly
            addSubmittedAnswersToExistingQuestionTemplateSnapshots(projectName, formTemplateVersion, submittedAnswerMap);
        } else {
            formTemplateVersion = new FormTemplateVersion(formTemplateVersionKey, formTemplate, null, null, contextUri);
            QuestionTemplateSnapshot questionTemplateSnapshot = createQuestionTemplateSnapshotsWithAnswers(
                    processor.getQuestionOriginsAndTheirPaths(),
                    submittedAnswerMap,
                    qaRemoteData,
                    formTemplateVersion,
                    projectName);
            formTemplateVersion.setQuestionTemplateSnapshot(questionTemplateSnapshot);
            formTemplateVersion = formTemplateVersionDAO.update(projectName, formTemplateVersion);
        }


        // RecordVersion
        String recordVersionKey = ObjectUtils.createKeyForContext(projectName, record.getUri() + "/" + processor.getAllQuestionOriginsAndAnswersHash());
        Optional<RecordVersion> recordVersionOpt = recordVersionDAO.findByKey(projectName, recordVersionKey);

        RecordVersion recordVersion;
        if (recordVersionOpt.isPresent()) {
            recordVersion = recordVersionOpt.get();
        } else {
            recordVersion = new RecordVersion(recordVersionKey, record);
            recordVersion = recordVersionDAO.update(projectName, recordVersion);
        }

        // RecordSnapshot
        RecordSnapshot recordSnapshot = new RecordSnapshot(recordSnapshotKey, record, recordVersion, formTemplateVersion, new HashSet<>(submittedAnswerMap.values()), recordRemoteData.getRecordModifiedDate(), contextUri);

        // re-update Record
        record.setFormTemplate(formTemplate);
        record.getRecordSnapshots().add(recordSnapshot);
        record.getRecordVersions().add(recordVersion);
        record = recordDAO.update(projectName, record);
    }

    private QuestionTemplateSnapshot createQuestionTemplateSnapshotsWithAnswers(
            Map<String, String> questionOriginAndTheirPaths,
            Map<String, SubmittedAnswer> submittedAnswerMap,
            QuestionSnapshotRemoteData questionTreeRemoteData,
            FormTemplateVersion formTemplateVersion,
            String projectDescriptorName) {

        QuestionTemplateSnapshotTreeBuilder qtsTreeBuilder = new QuestionTemplateSnapshotTreeBuilder(
                questionTreeRemoteData,
                questionOriginAndTheirPaths,
                formTemplateVersion,
                submittedAnswerMap,
                projectDescriptorName);

        return qtsTreeBuilder.process();
    }

    private void addSubmittedAnswersToExistingQuestionTemplateSnapshots(String projectDescriptorName, FormTemplateVersion formTemplateVersion, Map<String, SubmittedAnswer> submittedAnswerMap) {
        List<QuestionTemplateSnapshot> questionTemplateSnapshots = questionTemplateSnapshotDAO.findAllWhere(projectDescriptorName, Vocabulary.p_hasFormTemplateVersion, formTemplateVersion.getUri());
        questionTemplateSnapshots.stream().forEach(qts -> {
            SubmittedAnswer sa;
            if ((sa = submittedAnswerMap.get(qts.getQuestionOrigin())) != null) {
                if (qts.getAnswers() != null) {
                    boolean added = qts.getAnswers().add(sa);
                    if (added) questionTemplateSnapshotDAO.update(projectDescriptorName, qts);
                } else {
                    Set<SubmittedAnswer> answers = new HashSet<>();
                    answers.add(sa);
                    qts.setAnswers(answers);
                    questionTemplateSnapshotDAO.update(projectDescriptorName, qts);
                }
            }
        });
    }

    private Map<String, SubmittedAnswer> mapToSubmittedAnswersAndPersistEventually(Map<String, String> answeredQuestions, String projectDescriptorName) {
        // the result is question-origin-1 -> SubmittedAnswer(with question-origin-1)
        return answeredQuestions.entrySet().stream().map(entry -> {
            String answerKey = ObjectUtils.createKeyForContext(projectDescriptorName, entry.getKey() + entry.getValue()); // question-origin + answer-value
            return submittedAnswerDAO.findByKey(projectDescriptorName, answerKey)
                    .orElseGet(() -> submittedAnswerDAO.update(projectDescriptorName, new SubmittedAnswer(answerKey, entry.getValue(), entry.getKey())));
        }).collect(Collectors.toMap(SubmittedAnswer::getQuestionOrigin, Function.identity()));
    }
}
