package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.persisted.response.QuestionSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.model.persisted.response.SubmittedAnswerRemoteData;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class QuestionTreeRemoteDataProcessor {

    private QuestionSnapshotRemoteData rootQuestion;
    private String rootQuestionOrigin;

    private boolean isProcessed = false;

    private Map<String, String> questionOriginsAndTheirPaths = new HashMap<>(); // -> questionOriginPath for FormTemplateVersion, questionOrigin for QuestionTemplate
    private Map<String, String> answeredQuestions = new HashMap<>(); // question-origin + it's answer

    public QuestionTreeRemoteDataProcessor(QuestionSnapshotRemoteData rootQuestion) {
        this.rootQuestion = rootQuestion;
    }

    public void process() {
        rootQuestionOrigin = rootQuestion.getQuestionOrigin() != null ? rootQuestion.getQuestionOrigin() : "NO-QUESTION-ORIGIN";

        // search in the question & answer tree
        doDFS(rootQuestion, new LinkedList<>());
        isProcessed = true;
    }

    private void doDFS(QuestionSnapshotRemoteData questionSnapshotRemoteData, LinkedList<String> questionOriginPathBuilder) {

        // build question origin path while searching
        String qo = questionSnapshotRemoteData.getQuestionOrigin() != null ? questionSnapshotRemoteData.getQuestionOrigin() : "NO-QUESTION-ORIGIN";
        questionOriginPathBuilder.add(questionSnapshotRemoteData.getQuestionOrigin());

        // process answers for the current question
        processAnswers(questionSnapshotRemoteData, qo); // TODO: do non-leaf question have answers at all?

        // record current node's question-origin-path + question-origin
        String currentQuestionOriginPath = StringUtils.join(questionOriginPathBuilder);
        questionOriginsAndTheirPaths.put(qo, currentQuestionOriginPath);

        // terminate recursion at leaf nodes
        if (questionSnapshotRemoteData.getSubQuestions().isEmpty()) {
            questionOriginPathBuilder.pop();
            return;
        }

        // sort lexicographically and then search deeper
        questionSnapshotRemoteData.getSubQuestions().stream().sorted(Comparator.comparing(QuestionSnapshotRemoteData::getQuestionOrigin)).forEach(subQuestion -> {
            doDFS(subQuestion, questionOriginPathBuilder);
        });

        // remove current node
        questionOriginPathBuilder.pop();
    }

    private void processAnswers(QuestionSnapshotRemoteData questionSnapshotRemoteData, String questionOrigin) {
        List<String> answers = new ArrayList<>();
        for (SubmittedAnswerRemoteData answer : questionSnapshotRemoteData.getAnswers()) {
            if (answer.getTextValue() != null && !answer.getTextValue().isEmpty()) {
                answers.add(answer.getTextValue());
            } else if (answer.getCodeValue() != null) {
                answers.add(answer.getCodeValue().toString()); // TODO: handle object value
            }
        }
        if (!answers.isEmpty()) answeredQuestions.put(questionOrigin, answers.get(0));
    }

    public String getAllQuestionOriginsAndAnswersHash() {
        requireProcessed();
        return answeredQuestions.entrySet().stream().map(entry -> entry.getKey() + entry.getValue()).collect(Collectors.joining());
    }

    public String getAllQuestionOriginsPathsHash() {
        requireProcessed();
        return questionOriginsAndTheirPaths.values().stream().collect(Collectors.joining());
    }

    public Map<String, String> getQuestionOriginsAndTheirPaths() {
        requireProcessed();
        return questionOriginsAndTheirPaths;
    }

    public Map<String, String> getAnsweredQuestions() {
        requireProcessed();
        return answeredQuestions;
    }

    public String getRootQuestionOrigin() {
        requireProcessed();
        return rootQuestion.getQuestionOrigin();
    }

    private void requireProcessed() {
        if (!isProcessed) throw new IllegalStateException("Question tree not processed yet!");
    }
}
