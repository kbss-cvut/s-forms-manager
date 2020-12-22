package cz.cvut.kbss.sformsmanager.model.persisted;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.HasUniqueKey;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.FormGenMetadata)
public class FormGenMetadata implements Serializable, HasUniqueKey {

    @Id(generated = true)
    private URI uri;

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

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_key)
    private String key; // e.g. sm/formGen16453135

    public FormGenMetadata() {
    }

    public FormGenMetadata(URI uri, FormGenVersion formGenVersion, FormGenInstance formGenInstance, String contextUri, String connectionName) {
        this.uri = uri;
        this.formGenVersion = formGenVersion;
        this.formGenInstance = formGenInstance;
        this.contextUri = contextUri;
        this.connectionName = connectionName;
        this.key = createKey(connectionName, contextUri);
    }

    public FormGenMetadata(FormGenVersion formGenVersion, FormGenInstance formGenInstance, String contextUri, String connectionName) {
        this.formGenVersion = formGenVersion;
        this.formGenInstance = formGenInstance;
        this.contextUri = contextUri;
        this.connectionName = connectionName;
        this.key = createKey(connectionName, contextUri);
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

    public String getKey() {
        return key;
    }

    public void setKey(String connectionName, String contextUri) {
        this.key = OWLUtils.createInitialsAndConcatWithSlash(connectionName, contextUri);
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public FormGenInstance getFormGenInstance() {
        return formGenInstance;
    }

    public void setFormGenInstance(FormGenInstance formGenInstance) {
        this.formGenInstance = formGenInstance;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static String createKey(String connectionName, String contextUri) {
        return OWLUtils.createInitialsAndConcatWithSlash(connectionName, contextUri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormGenMetadata that = (FormGenMetadata) o;
        return Objects.equal(uri, that.uri) &&
                Objects.equal(formGenVersion, that.formGenVersion) &&
                Objects.equal(formGenInstance, that.formGenInstance) &&
                Objects.equal(contextUri, that.contextUri) &&
                Objects.equal(connectionName, that.connectionName) &&
                Objects.equal(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri, formGenVersion, formGenInstance, contextUri, connectionName, key);
    }
}
