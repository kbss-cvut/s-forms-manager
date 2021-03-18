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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public void processDataSnapshotInRemoteContext(String projectDescriptorName, URI contextUri) throws IOException {

        // TODO: use Optional.getOrElse and extract redundant code

        // LOAD REMOTE DATA: record snapshot
        RecordSnapshotRemoteData recordRemoteData = recordRemoteDAO.getRecordSnapshot(projectDescriptorName, contextUri);
        String recordSnapshotKey = ObjectUtils.getStringHashCode(contextUri);

        // Record
        String recordKey = recordRemoteData.getRemoteRecordURI() + "/" + recordRemoteData.getRecordCreateDate();
        Optional<Record> recordOpt = recordDAO.findByKey(projectDescriptorName, recordKey);
        Record record;
        if (recordOpt.isPresent()) {
            record = recordOpt.get();
        } else {
            record = recordDAO.update(projectDescriptorName, new Record(recordKey, null, recordRemoteData.getRecordCreateDate(), contextUri.toString()));
        }

        if (recordRemoteData.getQuestion() == null) {
            // initial record: no questions or answers variant

            // persist without RecordVersion and without FormTemplateVersion
            RecordSnapshot recordSnapshot = new RecordSnapshot(recordSnapshotKey, record, null, null, null, recordRemoteData.getRecordModifiedDate(), contextUri);
            recordSnapshotDAO.persist(projectDescriptorName, recordSnapshot);
            return;
        }

        // LOAD REMOTE DATA: question and answers snapshot
        QuestionSnapshotRemoteData qaRemoteData = questionsSnapshotRemoteDataDAO.getQuestionsAndAnswersSnapshot(projectDescriptorName, contextUri, recordRemoteData.getQuestion());

        // PROCESS the questions tree
        QuestionTreeRemoteDataProcessor processor = new QuestionTreeRemoteDataProcessor(qaRemoteData);
        processor.process();

        // Answers
        Map<String, SubmittedAnswer> submittedAnswerMap = mapToSubmittedAnswersAndPersistEventually(processor.getAnsweredQuestions(), projectDescriptorName);

        // FormTemplate
        String formTemplateKey = ObjectUtils.getStringHashCode(processor.getRootQuestionOrigin());
        Optional<FormTemplate> formTemplateOpt = formTemplateDAO.findByKey(projectDescriptorName, formTemplateKey);

        FormTemplate formTemplate;
        if (formTemplateOpt.isPresent()) {
            formTemplate = formTemplateOpt.get();
        } else {
            formTemplate = new FormTemplate(formTemplateKey);
            formTemplate = formTemplateDAO.update(projectDescriptorName, formTemplate);
        }

        // FormTemplateVersion
        String formTemplateVersionKey = processor.getAllQuestionOriginsPathsHash();
        Optional<FormTemplateVersion> formTemplateVersionOpt = formTemplateVersionDAO.findByKey(projectDescriptorName, formTemplateVersionKey);

        FormTemplateVersion formTemplateVersion;
        if (formTemplateVersionOpt.isPresent()) {
            formTemplateVersion = formTemplateVersionOpt.get();
            addSubmittedAnswersToExistingQuestionTemplateSnapshots(projectDescriptorName, formTemplateVersion, submittedAnswerMap);
        } else {
            QuestionTemplateSnapshot questionTemplateSnapshot = createQuestionTemplateSnapshotsWithAnswers(
                    processor.getQuestionOriginsAndTheirPaths(),
                    submittedAnswerMap,
                    qaRemoteData,
                    formTemplateVersionKey,
                    projectDescriptorName);
            formTemplateVersion = new FormTemplateVersion(formTemplateVersionKey, formTemplate, questionTemplateSnapshot, null, contextUri);
        }
        formTemplateVersionDAO.update(projectDescriptorName, formTemplateVersion);

        // re-update Record
        record.setFormTemplate(formTemplate);
        record = recordDAO.update(projectDescriptorName, record);

        // RecordVersion
        String recordVersionKey = recordRemoteData.getRemoteRecordURI() + "/" + processor.getAllQuestionOriginsAndAnswersHash();
        Optional<RecordVersion> recordVersionOpt = recordVersionDAO.findByKey(projectDescriptorName, recordVersionKey);

        RecordVersion recordVersion;
        if (recordVersionOpt.isPresent()) {
            recordVersion = recordVersionOpt.get();
        } else {
            recordVersion = new RecordVersion(recordVersionKey, record);
            recordVersion = recordVersionDAO.update(projectDescriptorName, recordVersion);
        }

        // RecordSnapshot
        RecordSnapshot recordSnapshot = new RecordSnapshot(recordSnapshotKey, record, recordVersion, formTemplateVersion, new HashSet<>(submittedAnswerMap.values()), recordRemoteData.getRecordModifiedDate(), contextUri);
        recordSnapshotDAO.persist(projectDescriptorName, recordSnapshot);
    }

    private QuestionTemplateSnapshot createQuestionTemplateSnapshotsWithAnswers(
            Map<String, String> questionOriginAndTheirPaths,
            Map<String, SubmittedAnswer> submittedAnswerMap,
            QuestionSnapshotRemoteData questionTreeRemoteData,
            String formTemplateVersionKey,
            String projectDescriptorName) {

        QuestionTemplateSnapshotTreeBuilder qtsTreeBuilder = new QuestionTemplateSnapshotTreeBuilder(
                questionTreeRemoteData,
                questionOriginAndTheirPaths,
                formTemplateVersionKey,
                submittedAnswerMap,
                projectDescriptorName,
                entity -> questionTemplateSnapshotDAO.update(projectDescriptorName, entity));

        return qtsTreeBuilder.process();
    }

    private void addSubmittedAnswersToExistingQuestionTemplateSnapshots(String projectDescriptorName, FormTemplateVersion formTemplateVersion, Map<String, SubmittedAnswer> submittedAnswerMap) {
        List<QuestionTemplateSnapshot> questionTemplateSnapshots = questionTemplateSnapshotDAO.findAllWhere(projectDescriptorName, Vocabulary.p_hasFormTemplateVersionKey, formTemplateVersion.getKey());
        questionTemplateSnapshots.stream().forEach(qts -> {
            SubmittedAnswer sa;
            if ((sa = submittedAnswerMap.get(qts.getQuestionOrigin())) != null) {
                boolean added = qts.getAnswers().add(sa);
                if (added) questionTemplateSnapshotDAO.persist(projectDescriptorName, qts);
            }
        });
    }

    private Map<String, SubmittedAnswer> mapToSubmittedAnswersAndPersistEventually(Map<String, String> answeredQuestions, String projectDesciptorName) {
        // the result is question-origin-1 -> SubmittedAnswer(with question-origin-1)
        return answeredQuestions.entrySet().stream().map(entry -> {
            String answerKey = ObjectUtils.getStringHashCode(entry.getKey() + entry.getValue()); // question-origin + answer-value
            return submittedAnswerDAO.findByKey(projectDesciptorName, answerKey)
                    .orElseGet(() -> submittedAnswerDAO.update(projectDesciptorName, new SubmittedAnswer(answerKey, entry.getValue(), entry.getKey())));
        }).collect(Collectors.toMap(SubmittedAnswer::getQuestionOrigin, Function.identity()));
    }
}
