package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.QuestionTemplateSnapshot;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.SubmittedAnswer;
import cz.cvut.kbss.sformsmanager.model.persisted.response.QuestionSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.utils.ObjectUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QuestionTemplateSnapshotTreeBuilder {

    private final QuestionSnapshotRemoteData remoteRootQuestion;
    private Map<String, String> questionOriginAndTheirPaths;
    private String formTemplateVersionKey;
    private Map<String, SubmittedAnswer> answerMap;
    private String connectionName;
    private Function<QuestionTemplateSnapshot, QuestionTemplateSnapshot> updateOperation;

    public QuestionTemplateSnapshotTreeBuilder(
            QuestionSnapshotRemoteData remoteRootQuestion,
            Map<String, String> questionOriginAndTheirPaths,
            String formTemplateVersionKey,
            Map<String, SubmittedAnswer> answerMap,
            String connectionName,
            Function<QuestionTemplateSnapshot, QuestionTemplateSnapshot> updateOperation) {

        this.remoteRootQuestion = remoteRootQuestion;
        this.questionOriginAndTheirPaths = questionOriginAndTheirPaths;
        this.formTemplateVersionKey = formTemplateVersionKey;
        this.answerMap = answerMap;
        this.connectionName = connectionName;
        this.updateOperation = updateOperation;
    }

    public QuestionTemplateSnapshot process() {
        return buildQTSTreeWithDFS(remoteRootQuestion);
    }

    private QuestionTemplateSnapshot buildQTSTreeWithDFS(QuestionSnapshotRemoteData questionRemoteData) {
        String qtsKey = ObjectUtils.getStringHashCode(formTemplateVersionKey + "/" + questionOriginAndTheirPaths.get(questionRemoteData.getQuestionOrigin()));
        if (questionRemoteData.getSubQuestions().isEmpty()) {
            QuestionTemplateSnapshot questionTemplateSnapshot = createQuestionTemplateSnapshot(qtsKey, questionRemoteData.getQuestionOrigin(), null);
            return updateOperation.apply(questionTemplateSnapshot);
        }

        Set<QuestionTemplateSnapshot> subQuestionTemplateSnapshots = questionRemoteData.getSubQuestions().stream()
                .map(qRemoteData ->
                        buildQTSTreeWithDFS(qRemoteData)
                ).collect(Collectors.toSet());

        QuestionTemplateSnapshot questionTemplateSnapshot = createQuestionTemplateSnapshot(qtsKey, questionRemoteData.getQuestionOrigin(), subQuestionTemplateSnapshots);
        return updateOperation.apply(questionTemplateSnapshot);
    }

    private QuestionTemplateSnapshot createQuestionTemplateSnapshot(String qtsKey, String questionOrigin, Set<QuestionTemplateSnapshot> subQuestionTemplateSnapshots) {
        return new QuestionTemplateSnapshot(
                qtsKey,
                formTemplateVersionKey,
                subQuestionTemplateSnapshots,
                questionOriginAndTheirPaths.get(questionOrigin),
                questionOrigin,
                new HashSet<SubmittedAnswer>() {{
                    if (answerMap.containsKey(questionOrigin)) {
                        add(answerMap.get(questionOrigin));
                    }
                }}
        );
    }
}
