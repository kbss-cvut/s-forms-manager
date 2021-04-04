package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.FormTemplateVersion;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.QuestionTemplateSnapshot;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.SubmittedAnswer;
import cz.cvut.kbss.sformsmanager.model.persisted.response.QuestionSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.utils.ObjectUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionTemplateSnapshotTreeBuilder {

    private final QuestionSnapshotRemoteData remoteRootQuestion;
    private Map<String, String> questionOriginAndTheirPaths;
    private FormTemplateVersion formTemplateVersion;
    private Map<String, SubmittedAnswer> answerMap;
    private String projectName;

    public QuestionTemplateSnapshotTreeBuilder(
            QuestionSnapshotRemoteData remoteRootQuestion,
            Map<String, String> questionOriginAndTheirPaths,
            FormTemplateVersion formTemplateVersion,
            Map<String, SubmittedAnswer> answerMap,
            String projectName) {

        this.remoteRootQuestion = remoteRootQuestion;
        this.questionOriginAndTheirPaths = questionOriginAndTheirPaths;
        this.formTemplateVersion = formTemplateVersion;
        this.answerMap = answerMap;
        this.projectName = projectName;
    }

    public QuestionTemplateSnapshot process() {
        return buildQTSTreeWithDFS(remoteRootQuestion, 0);
    }

    private QuestionTemplateSnapshot buildQTSTreeWithDFS(QuestionSnapshotRemoteData questionRemoteData, Integer depth) {
        String qtsKey = ObjectUtils.createKeyForContext(this.projectName, formTemplateVersion.getKey() + "/" + questionOriginAndTheirPaths.get(questionRemoteData.getQuestionOrigin()));
        if (questionRemoteData.getSubQuestions().isEmpty()) {
            return createQuestionTemplateSnapshot(qtsKey, questionRemoteData.getQuestionOrigin(), null, depth);
        }

        Set<QuestionTemplateSnapshot> subQuestionTemplateSnapshots = questionRemoteData.getSubQuestions().stream()
                .map(qRemoteData ->
                        buildQTSTreeWithDFS(qRemoteData, depth + 1)
                ).collect(Collectors.toSet());

        return createQuestionTemplateSnapshot(qtsKey, questionRemoteData.getQuestionOrigin(), subQuestionTemplateSnapshots, depth);
    }

    private QuestionTemplateSnapshot createQuestionTemplateSnapshot(String qtsKey, String questionOrigin, Set<QuestionTemplateSnapshot> subQuestionTemplateSnapshots, Integer depth) {
        return new QuestionTemplateSnapshot(
                qtsKey,
                subQuestionTemplateSnapshots,
                formTemplateVersion,
                questionOriginAndTheirPaths.get(questionOrigin),
                depth,
                questionOrigin,
                new HashSet<SubmittedAnswer>() {{
                    if (answerMap.containsKey(questionOrigin)) {
                        add(answerMap.get(questionOrigin));
                    }
                }}
        );
    }
}
