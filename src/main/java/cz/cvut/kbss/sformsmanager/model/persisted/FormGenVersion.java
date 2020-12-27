package cz.cvut.kbss.sformsmanager.model.persisted;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.HasUniqueKey;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.FormGenVersion)
public class FormGenVersion implements Serializable, HasUniqueKey {

    @Id(generated = true)
    private URI uri;

    /**
     * Represent a version of FormGenMetadata.
     * <p/>
     * Consists of connection name initials and its numbering.
     */
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_version)
    private String version; // e.g. v/sm/15

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_connectionName)
    private String connectionName;

    /**
     * Consists of connectionName initials and hashcode.
     * <p/>
     * Use {@link cz.cvut.kbss.sformsmanager.utils.OWLUtils#createInitialsAndConcatWithSlash(String, String)}
     */
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_key)
    private String key; // e.g. v/sm/13156432

    public FormGenVersion() {
    }

    public FormGenVersion(String connectionName, int versionNumbering, int hashcode) {
        this.connectionName = connectionName;
        this.version = createVersion(connectionName, versionNumbering);
        this.key = createKey(connectionName, hashcode);
    }

    public FormGenVersion(String connectionName, URI uri, String version, String key) {
        this.connectionName = connectionName;
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

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public static String createKey(String connectionName, int versionNumbering) {
        return "v/" + OWLUtils.createInitialsAndConcatWithSlash(connectionName, versionNumbering);
    }

    public static String createVersion(String connectionName, int hashcode) {
        return "v/" + OWLUtils.createInitialsAndConcatWithSlash(connectionName, hashcode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormGenVersion that = (FormGenVersion) o;
        return Objects.equal(uri, that.uri) &&
                Objects.equal(version, that.version) &&
                Objects.equal(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri, version, key);
    }
}
