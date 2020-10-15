package cz.cvut.kbss.sformsmanager.model.persisted;

import cz.cvut.kbss.jopa.model.BeanListenerAspect;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;
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
@OWLClass(iri = Vocabulary.FormGenMetadata)
public class FormGenMetadata extends BeanListenerAspect.ManageableImpl implements Serializable {

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
    private String key = OWLUtils.createFormGenMetadataKey(connectionName, contextUri);

}
