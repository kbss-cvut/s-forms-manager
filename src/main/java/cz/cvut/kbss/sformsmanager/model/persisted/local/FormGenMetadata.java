package cz.cvut.kbss.sformsmanager.model.persisted.local;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.FormGenMetadata)
public class FormGenMetadata extends LocalEntity implements Serializable, HasConnection {

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_assigned_version, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private FormGenVersion formGenVersion;

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_assigned_instance, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private FormGenInstance formGenInstance;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_contextUri)
    private String contextUri;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_connectionName)
    private String connectionName;

    public FormGenMetadata() {
    }

    public FormGenMetadata(URI uri, FormGenVersion formGenVersion, FormGenInstance formGenInstance, String contextUri, String connectionName) {
        super(uri, createKey(connectionName, contextUri));
        this.formGenVersion = formGenVersion;
        this.formGenInstance = formGenInstance;
        this.contextUri = contextUri;
        this.connectionName = connectionName;
    }

    public FormGenMetadata(FormGenVersion formGenVersion, FormGenInstance formGenInstance, String contextUri, String connectionName) {
        super(createKey(connectionName, contextUri));
        this.formGenVersion = formGenVersion;
        this.formGenInstance = formGenInstance;
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
        return Objects.equal(getUri(), that.getUri()) &&
                Objects.equal(formGenVersion, that.formGenVersion) &&
                Objects.equal(formGenInstance, that.formGenInstance) &&
                Objects.equal(contextUri, that.contextUri) &&
                Objects.equal(connectionName, that.connectionName) &&
                Objects.equal(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUri(), formGenVersion, formGenInstance, contextUri, connectionName, getKey());
    }
}
