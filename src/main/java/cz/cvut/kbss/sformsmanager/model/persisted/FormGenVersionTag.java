package cz.cvut.kbss.sformsmanager.model.persisted;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.FormGenVersion)
public class FormGenVersionTag implements Serializable {

    @Id(generated = true)
    private URI uri;

    /**
     * Represent a version of FormGenMetadata.
     */
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_version)
    private String version; // e.g. study-manager15

    /**
     * Consists of connectionName and hashcode.
     * <p/>
     * Use {@link cz.cvut.kbss.sformsmanager.utils.OWLUtils#createFormGenVersionTagKey(String, int)}
     */
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_key)
    private String key; // e.g. study-manager13156432

    public FormGenVersionTag() {
    }

    public FormGenVersionTag(String version, String key) {
        this.version = version;
        this.key = key;
    }

    public FormGenVersionTag(URI uri, String version, String key) {
        this.uri = uri;
        this.version = version;
        this.key = key;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
        FormGenVersionTag that = (FormGenVersionTag) o;
        return Objects.equal(uri, that.uri) &&
                Objects.equal(version, that.version) &&
                Objects.equal(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri, version, key);
    }
}
