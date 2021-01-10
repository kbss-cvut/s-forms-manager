package cz.cvut.kbss.sformsmanager.model.persisted.local;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.model.persisted.HasUniqueKey;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.FormGenVersion)
public class FormGenVersion extends LocalEntity implements Serializable, HasUniqueKey, HasConnection {

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

    public FormGenVersion() {
    }

    public FormGenVersion(String connectionName, int versionNumbering, int hashcode) {
        super(createKey(connectionName, hashcode));
        this.connectionName = connectionName;
        this.version = createVersion(connectionName, versionNumbering);
    }

    public FormGenVersion(String connectionName, URI uri, String version, String key) {
        super(uri, key);
        this.connectionName = connectionName;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Consists of connectionName initials and hashcode.
     * <p/>
     * Use {@link cz.cvut.kbss.sformsmanager.utils.OWLUtils#createInitialsAndConcatWithSlash(String, String)}
     */
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
        return Objects.equal(getUri(), that.getUri()) &&
                Objects.equal(version, that.version) &&
                Objects.equal(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUri(), version, getKey());
    }
}
