package cz.cvut.kbss.sformsmanager.model.persisted.local.gen2;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;

import java.io.Serializable;
import java.util.Date;

@OWLClass(iri = Vocabulary.FormGenMetadata)
public class Record extends LocalEntity implements Serializable, HasConnection {

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_hasFormTemplate, fetch = FetchType.LAZY)
    private FormTemplate formTemplate;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_recordCreated)
    private Date recordCreated;

    // key is the RECORD-URI + CREATED -> hash

    @Transient
    private String connectionName; // used for creating sparql descriptor

    public Record() {
    }

    public Record(String recordUriAndCreatedHashKey, FormTemplate formTemplate, Date recordCreated, String connectionName) {
        super(recordUriAndCreatedHashKey);
        this.formTemplate = formTemplate;
        this.recordCreated = recordCreated;
        this.connectionName = connectionName;
    }

    public Date getRecordCreated() {
        return recordCreated;
    }

    public void setRecordCreated(Date recordCreated) {
        this.recordCreated = recordCreated;
    }

    @Override
    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public FormTemplate getFormTemplate() {
        return formTemplate;
    }

    public void setFormTemplate(FormTemplate formTemplate) {
        this.formTemplate = formTemplate;
    }
}

