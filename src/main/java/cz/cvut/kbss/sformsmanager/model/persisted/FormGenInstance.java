package cz.cvut.kbss.sformsmanager.model.persisted;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.FormGenInstance)
public class FormGenInstance implements Serializable {

    @Id(generated = true)
    private URI uri;

    /**
     * Represent a numbering of FormGen completion.
     * <p/>
     * Consists of connectionName initials, contextUri and its number of completion.
     */
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_instance)
    private String instance; // e.g. i/sm/8

    /**
     * Consists of connectionName, contextUri and hashcode.
     * <p/>
     * Use {@link cz.cvut.kbss.sformsmanager.utils.OWLUtils#createInitialsAndConcatWithSlash(String, String)}}
     */
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_key)
    private String key; // e.g. i/sm/formGen13156432

    public FormGenInstance(String connectionName, String contextUri, int numbering) {
        this.instance = createInstance(connectionName, numbering);
        this.key = createKey(connectionName, contextUri);
    }

    public FormGenInstance(URI uri, String instance, String key) {
        this.uri = uri;
        this.instance = instance;
        this.key = key;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static String createInstance(String connectionName, int numbering) {
        return "i/" + OWLUtils.createInitialsAndConcatWithSlash(connectionName, numbering);
    }

    public static String createKey(String connectionName, String contextUri) {
        return "i/" + OWLUtils.createInitialsAndConcatWithSlash(connectionName, contextUri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormGenInstance that = (FormGenInstance) o;
        return Objects.equal(uri, that.uri) &&
                Objects.equal(instance, that.instance) &&
                Objects.equal(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri, instance, key);
    }
}
