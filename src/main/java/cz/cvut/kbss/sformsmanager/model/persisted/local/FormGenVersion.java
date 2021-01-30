package cz.cvut.kbss.sformsmanager.model.persisted.local;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.FormGenVersion)
public class FormGenVersion extends LocalEntity implements Serializable, HasConnection {

    /**
     * Represent a version of FormGenMetadata.
     * <p/>
     * Consists of connection name initials and its numbering.
     */
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_versionName)
    private String versionName; // e.g. v/sm/15

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_contextUri)
    private String sampleContextUri;

    @ParticipationConstraints()
    @OWLDataProperty(iri = Vocabulary.p_synonym)
    private String synonym;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_connectionName)
    private String connectionName;

    public FormGenVersion() {
    }

    public FormGenVersion(String connectionName, int versionNumbering, String hash, String sampleContextUri) {
        super(createKey(connectionName, hash));
        this.connectionName = connectionName;
        this.versionName = createVersion(connectionName, versionNumbering);
        this.sampleContextUri = sampleContextUri;
    }

    public FormGenVersion(String connectionName, URI uri, String versionName, String key, String sampleContextUri) {
        super(uri, key);
        this.connectionName = connectionName;
        this.versionName = versionName;
        this.sampleContextUri = sampleContextUri;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getSampleContextUri() {
        return sampleContextUri;
    }

    public void setSampleContextUri(String sampleContextUri) {
        this.sampleContextUri = sampleContextUri;
    }

    /**
     * Consists of connectionName initials and numbering of version.
     * <p/>
     * Use {@link cz.cvut.kbss.sformsmanager.utils.OWLUtils#createInitialsAndConcatWithSlash(String, String)}
     */
    public static String createVersion(String connectionName, int versionNumbering) {
        return "v/" + OWLUtils.createInitialsAndConcatWithSlash(connectionName, versionNumbering);
    }

    public static String createKey(String connectionName, String hash) {
        return "v/" + OWLUtils.createInitialsAndConcatWithSlash(connectionName, hash);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormGenVersion that = (FormGenVersion) o;
        return Objects.equal(getUri(), that.getUri()) &&
                Objects.equal(versionName, that.versionName) &&
                Objects.equal(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUri(), versionName, getKey());
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }
}
