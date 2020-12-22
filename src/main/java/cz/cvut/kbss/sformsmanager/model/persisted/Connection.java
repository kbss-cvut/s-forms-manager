package cz.cvut.kbss.sformsmanager.model.persisted;


import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.HasUniqueKey;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.net.URI;

@OWLClass(iri = Vocabulary.Connection)
public class Connection implements Serializable, HasUniqueKey {

    @Id(generated = true)
    private URI uri;

    @NotBlank
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_repositoryUrl)
    private String formGenRepositoryUrl;

    @NotBlank
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_serviceUrl)
    private String formGenServiceUrl;

    @NotBlank
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_appRepositoryUrl)
    private String appRepositoryUrl;

    @NotBlank
    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_key)
    private String key; // connectionName

    public Connection() {
    }

    public Connection(String formGenRepositoryUrl, String formGenServiceUrl, String appRepositoryUrl, String connectionName) {
        this.formGenRepositoryUrl = formGenRepositoryUrl;
        this.formGenServiceUrl = formGenServiceUrl;
        this.appRepositoryUrl = appRepositoryUrl;
        this.key = connectionName;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getFormGenRepositoryUrl() {
        return formGenRepositoryUrl;
    }

    public void setFormGenRepositoryUrl(String formGenRepositoryUrl) {
        this.formGenRepositoryUrl = formGenRepositoryUrl;
    }

    public String getFormGenServiceUrl() {
        return formGenServiceUrl;
    }

    public void setFormGenServiceUrl(String formGenServiceUrl) {
        this.formGenServiceUrl = formGenServiceUrl;
    }

    public String getAppRepositoryUrl() {
        return appRepositoryUrl;
    }

    public void setAppRepositoryUrl(String appRepositoryUrl) {
        this.appRepositoryUrl = appRepositoryUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
