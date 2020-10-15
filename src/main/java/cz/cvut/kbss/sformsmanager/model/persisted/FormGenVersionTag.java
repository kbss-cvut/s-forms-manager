package cz.cvut.kbss.sformsmanager.model.persisted;

import cz.cvut.kbss.jopa.model.BeanListenerAspect;
import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URI;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@OWLClass(iri = Vocabulary.FormGenVersion)
public class FormGenVersionTag extends BeanListenerAspect.ManageableImpl implements Serializable {

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

}
