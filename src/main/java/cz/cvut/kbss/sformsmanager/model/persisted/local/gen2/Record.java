package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;
import java.util.Date;

@OWLClass(iri = Vocabulary.Record)
public class Record extends LocalEntity implements Serializable {

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_hasFormTemplate)
    private FormTemplate formTemplate;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_recordCreated)
    private Date recordCreated;

    // key is the RECORD-URI + CREATED -> hash

    public Record() {
    }

    public Record(String recordUriAndCreatedHashKey, FormTemplate formTemplate, Date recordCreated) {
        super(recordUriAndCreatedHashKey);
        this.formTemplate = formTemplate;
        this.recordCreated = recordCreated;
    }

    public Date getRecordCreated() {
        return recordCreated;
    }

    public void setRecordCreated(Date recordCreated) {
        this.recordCreated = recordCreated;
    }

    public FormTemplate getFormTemplate() {
        return formTemplate;
    }

    public void setFormTemplate(FormTemplate formTemplate) {
        this.formTemplate = formTemplate;
    }
}

