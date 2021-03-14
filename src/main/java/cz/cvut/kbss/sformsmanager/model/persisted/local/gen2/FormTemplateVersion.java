package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;


import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.FormTemplateVersion)
public class FormTemplateVersion extends LocalEntity implements Serializable, HasConnection {

    @ParticipationConstraints
    @OWLObjectProperty(iri = Vocabulary.p_hasFormTemplate, fetch = FetchType.LAZY)
    private FormTemplate formTemplate;

    @ParticipationConstraints
    @OWLObjectProperty(iri = Vocabulary.p_hasQuestionTemplateSnapshot, fetch = FetchType.EAGER)
    private QuestionTemplateSnapshot questionTemplateSnapshot;

    @Transient
    private String connectionName;

    // key is sorted all the QUESTION-ORIGINS -> hash

    public FormTemplateVersion() {
    }

    public FormTemplateVersion(String questionOriginsHash, FormTemplate formTemplate, QuestionTemplateSnapshot questionTemplateSnapshot, String connectionName) {
        super(questionOriginsHash);
        this.formTemplate = formTemplate;
        this.questionTemplateSnapshot = questionTemplateSnapshot;
        this.connectionName = connectionName;
    }

    @Override
    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
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
