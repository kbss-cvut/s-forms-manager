package cz.cvut.kbss.sformsmanager.model.persisted.local;

import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.MappedSuperclass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasUniqueKey;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.net.URI;

/**
 * Locally persisted entity. Not an entity retrieved from connected databases.
 */
@MappedSuperclass
public class LocalEntity implements HasUniqueKey {

    @Id(generated = true)
    private URI uri;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_key)
    private String key;

    public LocalEntity() {
    }

    public LocalEntity(String key) {
        this.key = key;
    }

    public LocalEntity(URI uri, String key) {
        this.uri = uri;
        this.key = key;
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

    public void setKey(String key) {
        this.key = key;
    }

}
