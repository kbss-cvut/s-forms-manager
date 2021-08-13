package cz.cvut.kbss.sformsmanager.model.persisted.local;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@OWLClass(iri = Vocabulary.Record)
public class Record extends LocalEntity implements Serializable {

    @ParticipationConstraints(nonEmpty = true)
    @OWLObjectProperty(iri = Vocabulary.p_hasRecordSnapshots, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<RecordSnapshot> recordSnapshots;

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_hasRecordVersions, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<RecordVersion> recordVersions;

    @OWLObjectProperty(iri = Vocabulary.p_hasFormTemplate)
    private FormTemplate formTemplate;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_recordCreated)
    private Date recordCreated;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_hasRemoteContextURI)
    private String remoteContextURI;

    // key is the RECORD-URI + CREATED -> hash

    public Record() {
    }

    public Record(String recordUriAndCreatedHashKey, Set<RecordSnapshot> recordSnapshots, Set<RecordVersion> recordVersions, FormTemplate formTemplate, Date recordCreated, String remoteContextURI) {
        super(recordUriAndCreatedHashKey);
        this.recordSnapshots = recordSnapshots;
        this.recordVersions = recordVersions;
        this.formTemplate = formTemplate;
        this.recordCreated = recordCreated;
        this.remoteContextURI = remoteContextURI;
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

    public String getRemoteContextURI() {
        return remoteContextURI;
    }

    public void setRemoteContextURI(String remoteContextURI) {
        this.remoteContextURI = remoteContextURI;
    }

    public Set<RecordSnapshot> getRecordSnapshots() {
        if (this.recordSnapshots == null) {
            this.recordSnapshots = new HashSet<>();
        }
        return recordSnapshots;
    }

    public void setRecordSnapshots(Set<RecordSnapshot> recordSnapshots) {
        this.recordSnapshots = recordSnapshots;
    }

    public Set<RecordVersion> getRecordVersions() {
        if (this.recordVersions == null) {
            this.recordVersions = new HashSet<>();
        }
        return recordVersions;
    }

    public void setRecordVersions(Set<RecordVersion> recordVersions) {
        this.recordVersions = recordVersions;
    }
}

