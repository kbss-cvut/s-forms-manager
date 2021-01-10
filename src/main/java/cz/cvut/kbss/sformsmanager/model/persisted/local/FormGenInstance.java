package cz.cvut.kbss.sformsmanager.model.persisted.local;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.FormGenInstance)
public class FormGenInstance extends LocalEntity implements Serializable, HasConnection {

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_connectionName)
    private String connectionName;

    public FormGenInstance() {
    }

    public FormGenInstance(String connectionName, String contextUri, int numbering) {
        super(createInstance(connectionName, numbering));
        this.connectionName = connectionName;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Represent a numbering of FormGen completion. TODO: ???
     * <p/>
     * Consists of connectionName initials, contextUri and its number of completion.
     */
    public static String createInstance(String connectionName, int numbering) {
        return "i/" + OWLUtils.createInitialsAndConcatWithSlash(connectionName, numbering);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormGenInstance that = (FormGenInstance) o;
        return Objects.equal(getUri(), that.getUri()) &&
                Objects.equal(getUri(), that.getUri()) &&
                Objects.equal(connectionName, that.connectionName) &&
                Objects.equal(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUri(), connectionName, getUri(), getKey());
    }
}
