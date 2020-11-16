package cz.cvut.kbss.sformsmanager.model.persisted;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.FormGenMetadata)
public class FormGenMetadata implements Serializable {

    @Id(generated = true)
    private URI uri;

    @ParticipationConstraints()
    @OWLObjectProperty(iri = Vocabulary.p_assigned_version_tag, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private FormGenVersionTag versionTag;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_contextUri)
    private String contextUri;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_connectionName)
    private String connectionName;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_key)
    private String key = OWLUtils.createFormGenkey(connectionName, contextUri);

    public FormGenMetadata() {
    }

    public FormGenMetadata(URI uri, FormGenVersionTag versionTag, String contextUri, String connectionName, String key) {
        this.uri = uri;
        this.versionTag = versionTag;
        this.contextUri = contextUri;
        this.connectionName = connectionName;
        this.key = key;
    }

    public FormGenMetadata(FormGenVersionTag versionTag, String contextUri, String connectionName, String key) {
        this.versionTag = versionTag;
        this.contextUri = contextUri;
        this.connectionName = connectionName;
        this.key = key;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public FormGenVersionTag getVersionTag() {
        return versionTag;
    }

    public void setVersionTag(FormGenVersionTag versionTag) {
        this.versionTag = versionTag;
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

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormGenMetadata that = (FormGenMetadata) o;
        return Objects.equal(uri, that.uri) &&
                Objects.equal(versionTag, that.versionTag) &&
                Objects.equal(contextUri, that.contextUri) &&
                Objects.equal(connectionName, that.connectionName) &&
                Objects.equal(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri, versionTag, contextUri, connectionName, key);
    }
}
