package cz.cvut.kbss.sformsmanager.model.persisted.local;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

@OWLClass(iri = Vocabulary.FormGenMetadata)
public class FormGenMetadata extends LocalEntity implements Serializable, HasConnection {

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_assigned_version, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private FormGenVersion formGenVersion;

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_assigned_instance, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private FormGenInstance formGenInstance;

    @OWLDataProperty(iri = Vocabulary.p_save_hash)
    private String formGenSaveHash;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_formGen_created)
    private Date formGenCreated;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_contextUri)
    private String contextUri;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_connectionName)
    private String connectionName;

    public FormGenMetadata() {
    }

    public FormGenMetadata(URI uri, FormGenVersion formGenVersion, FormGenInstance formGenInstance, String formGenSaveHash, Date formGenCreated, String contextUri, String connectionName) {
        super(uri, createKey(connectionName, contextUri));
        this.formGenVersion = formGenVersion;
        this.formGenInstance = formGenInstance;
        this.formGenSaveHash = formGenSaveHash;
        this.formGenCreated = formGenCreated;
        this.contextUri = contextUri;
        this.connectionName = connectionName;
    }

    public FormGenMetadata(FormGenVersion formGenVersion, FormGenInstance formGenInstance, String formGenSaveHash, Date formGenCreated, String contextUri, String connectionName) {
        super(createKey(connectionName, contextUri));
        this.formGenVersion = formGenVersion;
        this.formGenInstance = formGenInstance;
        this.formGenSaveHash = formGenSaveHash;
        this.formGenCreated = formGenCreated;
        this.contextUri = contextUri;
        this.connectionName = connectionName;
    }

    public FormGenVersion getFormGenVersion() {
        return formGenVersion;
    }

    public void setFormGenVersion(FormGenVersion formGenVersion) {
        this.formGenVersion = formGenVersion;
    }

    public String getContextUri() {
        return contextUri;
    }

    public void setContextUri(String contextUri) {
        this.contextUri = contextUri;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }


    public FormGenInstance getFormGenInstance() {
        return formGenInstance;
    }

    public String getFormGenSaveHash() {
        return formGenSaveHash;
    }

    public void setFormGenSaveHash(String formGenSaveHash) {
        this.formGenSaveHash = formGenSaveHash;
    }

    public Date getFormGenCreated() {
        return formGenCreated;
    }

    public void setFormGenCreated(Date formGenCreated) {
        this.formGenCreated = formGenCreated;
    }

    public void setFormGenInstance(FormGenInstance formGenInstance) {
        this.formGenInstance = formGenInstance;
    }

    public static String createKey(String connectionName, String contextUri) {
        return OWLUtils.createInitialsAndConcatWithSlash(connectionName, contextUri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormGenMetadata that = (FormGenMetadata) o;
        return Objects.equal(formGenVersion, that.formGenVersion) &&
                Objects.equal(formGenInstance, that.formGenInstance) &&
                Objects.equal(formGenSaveHash, that.formGenSaveHash) &&
                Objects.equal(formGenCreated, that.formGenCreated) &&
                Objects.equal(contextUri, that.contextUri) &&
                Objects.equal(connectionName, that.connectionName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(formGenVersion, formGenInstance, formGenSaveHash, formGenCreated, contextUri, connectionName);
    }
}
