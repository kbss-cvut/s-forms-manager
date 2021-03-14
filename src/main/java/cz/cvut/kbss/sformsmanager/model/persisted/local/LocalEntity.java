package cz.cvut.kbss.sformsmanager.model.persisted.local;

import com.google.common.base.Objects;
import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.MappedSuperclass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasUniqueKey;

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

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalEntity)) return false;
        LocalEntity that = (LocalEntity) o;
        return Objects.equal(getUri(), that.getUri()) &&
                Objects.equal(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUri(), getKey());
    }
}
