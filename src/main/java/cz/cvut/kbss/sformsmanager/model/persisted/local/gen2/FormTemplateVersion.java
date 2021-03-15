package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;


import cz.cvut.kbss.jopa.model.annotations.FetchType;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.FormTemplateVersion)
public class FormTemplateVersion extends LocalEntity implements Serializable {

    @ParticipationConstraints
    @OWLObjectProperty(iri = Vocabulary.p_hasFormTemplate, fetch = FetchType.LAZY)
    private FormTemplate formTemplate;

    @ParticipationConstraints
    @OWLObjectProperty(iri = Vocabulary.p_hasQuestionTemplateSnapshot, fetch = FetchType.EAGER)
    private QuestionTemplateSnapshot questionTemplateSnapshot;

    // key is sorted all the QUESTION-ORIGINS -> hash

    public FormTemplateVersion() {
    }

    public FormTemplateVersion(String questionOriginsHash, FormTemplate formTemplate, QuestionTemplateSnapshot questionTemplateSnapshot) {
        super(questionOriginsHash);
        this.formTemplate = formTemplate;
        this.questionTemplateSnapshot = questionTemplateSnapshot;
    }

    public QuestionTemplateSnapshot getQuestionTemplateSnapshot() {
        return questionTemplateSnapshot;
    }

    public void setQuestionTemplateSnapshot(QuestionTemplateSnapshot questionTemplateSnapshot) {
        this.questionTemplateSnapshot = questionTemplateSnapshot;
    }

    public FormTemplate getFormTemplate() {
        return formTemplate;
    }

    public void setFormTemplate(FormTemplate formTemplate) {
        this.formTemplate = formTemplate;
    }
}
