package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@OWLClass(iri = Vocabulary.QuestionTemplateSnapshot)
public class QuestionTemplateSnapshot extends LocalEntity implements Serializable, HasConnection {

    @Sequence
    @OWLObjectProperty(iri = Vocabulary.p_hasQuestionTemplateSnapshots)
    private List<QuestionTemplateSnapshot> questionTemplateSnapshots;

    @OWLDataProperty(iri = Vocabulary.p_hasFormTemplateVersionKey)
    private String formTemplateVersionKey;

    @OWLDataProperty(iri = Vocabulary.p_originPathsHash)
    private String questionOriginPath;

    @OWLDataProperty(iri = Vocabulary.s_p_has_question_origin)
    private String questionOrigin;

    @OWLObjectProperty(iri = Vocabulary.p_hasSubmittedAnswers)
    private Set<SubmittedAnswer> answers;

    // key is the QUESTION-ORIGIN-PATH + FORM-TEMPLATE-VERSION -> hash

    @Transient
    private String connectionName;

    public QuestionTemplateSnapshot() {
    }

    public QuestionTemplateSnapshot(
            String formTemplateVersionAndOriginPathHashKey,
            String formTemplateVersionKey,
            List<QuestionTemplateSnapshot> questionTemplateSnapshots,
            String questionOriginPath,
            String questionOrigin,
            Set<SubmittedAnswer> answers,
            String connectionName) {

        super(formTemplateVersionAndOriginPathHashKey);
        this.formTemplateVersionKey = formTemplateVersionKey;
        this.questionTemplateSnapshots = questionTemplateSnapshots;
        this.questionOriginPath = questionOriginPath;
        this.questionOrigin = questionOrigin;
        this.answers = answers;
        this.connectionName = connectionName;
    }

    public String getQuestionOriginPath() {
        return questionOriginPath;
    }

    public void setQuestionOriginPath(String questionOriginPath) {
        this.questionOriginPath = questionOriginPath;
    }

    public Set<SubmittedAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<SubmittedAnswer> answers) {
        this.answers = answers;
    }

    @Override
    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getQuestionOrigin() {
        return questionOrigin;
    }

    public void setQuestionOrigin(String questionOrigin) {
        this.questionOrigin = questionOrigin;
    }

    public List<QuestionTemplateSnapshot> getQuestionTemplateSnapshots() {
        return questionTemplateSnapshots;
    }

    public void setQuestionTemplateSnapshots(List<QuestionTemplateSnapshot> questionTemplateSnapshots) {
        this.questionTemplateSnapshots = questionTemplateSnapshots;
    }

    public String getFormTemplateVersionKey() {
        return formTemplateVersionKey;
    }

    public void setFormTemplateVersionKey(String formTemplateVersionKey) {
        this.formTemplateVersionKey = formTemplateVersionKey;
    }

}
