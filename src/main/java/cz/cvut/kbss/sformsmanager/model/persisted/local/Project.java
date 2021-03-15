package cz.cvut.kbss.sformsmanager.model.persisted.local;


import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasUniqueKey;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@OWLClass(iri = Vocabulary.Project)
public class Project extends LocalEntity implements Serializable, HasUniqueKey {

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

    public Project() {
    }

    public Project(String formGenRepositoryUrl, String formGenServiceUrl, String appRepositoryUrl, String projectName) {
        super(projectName);
        this.formGenRepositoryUrl = formGenRepositoryUrl;
        this.formGenServiceUrl = formGenServiceUrl;
        this.appRepositoryUrl = appRepositoryUrl;
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
}
