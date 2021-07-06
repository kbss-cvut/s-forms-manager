package cz.cvut.kbss.sformsmanager.model.persisted.local;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;

import java.io.Serializable;
import java.util.Set;

@OWLClass(iri = Vocabulary.QuestionTemplateSnapshot)
public class QuestionTemplateSnapshot extends LocalEntity implements Serializable {

    @OWLObjectProperty(iri = Vocabulary.p_hasQuestionTemplate, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private QuestionTemplate questionTemplate;

    @OWLObjectProperty(iri = Vocabulary.p_hasQuestionTemplateSnapshots, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<QuestionTemplateSnapshot> questionTemplateSnapshots;

    @ParticipationConstraints(nonEmpty = true)
    @OWLObjectProperty(iri = Vocabulary.p_hasFormTemplateVersion)
    private FormTemplateVersion formTemplateVersion;

    @OWLDataProperty(iri = Vocabulary.p_questionOriginPath)
    private String questionOriginPath;

    @OWLDataProperty(iri = Vocabulary.s_p_has_question_origin)
    private String questionOrigin;

    @OWLDataProperty(iri = Vocabulary.s_p_label)
    private String label;

    @OWLObjectProperty(iri = Vocabulary.p_hasSubmittedAnswers)
    private Set<SubmittedAnswer> answers;

    // key is the QUESTION-ORIGIN-PATH + FORM-TEMPLATE-VERSION -> hash

    public QuestionTemplateSnapshot() {
    }

    public QuestionTemplateSnapshot(
            String formTemplateVersionAndOriginPathHashKey,
            QuestionTemplate questionTemplate, Set<QuestionTemplateSnapshot> questionTemplateSnapshots,
            FormTemplateVersion formTemplateVersion,
            String questionOriginPath,
            String questionOrigin,
            String label,
            Set<SubmittedAnswer> answers) {

        super(formTemplateVersionAndOriginPathHashKey);
        this.questionTemplate = questionTemplate;
        this.formTemplateVersion = formTemplateVersion;
        this.questionTemplateSnapshots = questionTemplateSnapshots;
        this.questionOriginPath = questionOriginPath;
        this.questionOrigin = questionOrigin;
        this.label = label;
        this.answers = answers;
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

    public String getQuestionOrigin() {
        return questionOrigin;
    }

    public void setQuestionOrigin(String questionOrigin) {
        this.questionOrigin = questionOrigin;
    }

    public Set<QuestionTemplateSnapshot> getQuestionTemplateSnapshots() {
        return questionTemplateSnapshots;
    }

    public void setQuestionTemplateSnapshots(Set<QuestionTemplateSnapshot> questionTemplateSnapshots) {
        this.questionTemplateSnapshots = questionTemplateSnapshots;
    }

    public FormTemplateVersion getFormTemplateVersion() {
        return formTemplateVersion;
    }

    public void setFormTemplateVersion(FormTemplateVersion formTemplateVersion) {
        this.formTemplateVersion = formTemplateVersion;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public QuestionTemplate getQuestionTemplate() {
        return questionTemplate;
    }

    public void setQuestionTemplate(QuestionTemplate questionTemplate) {
        this.questionTemplate = questionTemplate;
    }
}
