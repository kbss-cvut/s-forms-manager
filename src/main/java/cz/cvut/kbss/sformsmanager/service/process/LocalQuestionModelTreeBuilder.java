package cz.cvut.kbss.sformsmanager.service.process;

import com.github.jsonldjava.shaded.com.google.common.collect.Sets;
import cz.cvut.kbss.sformsmanager.model.persisted.local.*;
import cz.cvut.kbss.sformsmanager.model.persisted.response.QuestionSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.utils.ObjectUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LocalQuestionModelTreeBuilder {

    private final QuestionSnapshotRemoteData remoteRootQuestion;
    private final Map<String, QuestionTemplate> questionTemplatesMap;
    private final Map<String, String> questionOriginAndTheirPaths;
    private final FormTemplate formTemplate;
    private final FormTemplateVersion formTemplateVersion;
    private final Map<String, SubmittedAnswer> answerMap;
    private final String projectName;

    public LocalQuestionModelTreeBuilder(
            QuestionSnapshotRemoteData remoteRootQuestion,
            Map<String, QuestionTemplate> questionTemplatesMap, Map<String, String> questionOriginAndTheirPaths,
            FormTemplate formTemplate, FormTemplateVersion formTemplateVersion,
            Map<String, SubmittedAnswer> answerMap,
            String projectName) {

        this.remoteRootQuestion = remoteRootQuestion;
        this.questionTemplatesMap = questionTemplatesMap;
        this.questionOriginAndTheirPaths = questionOriginAndTheirPaths;
        this.formTemplate = formTemplate;
        this.formTemplateVersion = formTemplateVersion;
        this.answerMap = answerMap;
        this.projectName = projectName;
    }

    /**
     * Walks the remote questions tree recursively and builds QuestionsTemplateSnapshot for each question. Also if it does not already exists, it creates QuestionTemplate(s).
     * <p/>
     * Does not persist questions directly.
     *
     * @return
     */
    public QuestionTemplateSnapshot process() {
        return buildQTSTreeWithDFS(remoteRootQuestion);
    }

    private QuestionTemplateSnapshot buildQTSTreeWithDFS(QuestionSnapshotRemoteData questionRemoteData) {
        String qtsKey = ObjectUtils.createKeyForContext(this.projectName, formTemplateVersion.getKey() + "/" + questionOriginAndTheirPaths.get(questionRemoteData.getQuestionOrigin()));
        if (questionRemoteData.getSubQuestions().isEmpty()) {
            return createQuestionTemplateAndSnapshot(qtsKey, questionRemoteData.getQuestionOrigin(), null, questionRemoteData.getLabel());
        }

        Set<QuestionTemplateSnapshot> subQuestionTemplateSnapshots = questionRemoteData.getSubQuestions().stream()
                .map(qRemoteData ->
                        buildQTSTreeWithDFS(qRemoteData)
                ).collect(Collectors.toSet());

        return createQuestionTemplateAndSnapshot(qtsKey, questionRemoteData.getQuestionOrigin(), subQuestionTemplateSnapshots, questionRemoteData.getLabel());
    }

    private QuestionTemplateSnapshot createQuestionTemplateAndSnapshot(String qtsKey, String questionOrigin, Set<QuestionTemplateSnapshot> subQuestionTemplateSnapshots, String label) {
        QuestionTemplate qt = questionTemplatesMap.computeIfAbsent(questionOrigin, s -> {
            // key = has(root question QO + current QO)
            String qtKey = ObjectUtils.createKeyForContext(projectName, remoteRootQuestion.getQuestionOrigin() + questionOrigin);
            return new QuestionTemplate(qtKey, formTemplate, questionOrigin, Sets.newHashSet());
        });

        QuestionTemplateSnapshot qts = new QuestionTemplateSnapshot(
                qtsKey,
                qt,
                subQuestionTemplateSnapshots,
                formTemplateVersion,
                questionOriginAndTheirPaths.get(questionOrigin),
                questionOrigin,
                label,
                new HashSet<SubmittedAnswer>() {{
                    if (answerMap.containsKey(questionOrigin)) {
                        add(answerMap.get(questionOrigin));
                    }
                }}
        );

        qt.getSnapshots().add(qts);

        // is persisted later
        return qts;
    }
}
