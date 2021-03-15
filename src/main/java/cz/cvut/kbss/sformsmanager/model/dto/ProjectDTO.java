package cz.cvut.kbss.sformsmanager.model.dto;


import cz.cvut.kbss.sformsmanager.model.persisted.local.Project;

public class ProjectDTO {

    private String formGenRepositoryUrl;
    private String formGenServiceUrl;
    private String appRepositoryUrl;
    private String connectionName;

    public ProjectDTO() {
    }

    public ProjectDTO(String formGenRepositoryUrl, String formGenServiceUrl, String appRepositoryUrl, String connectionName) {
        this.formGenRepositoryUrl = formGenRepositoryUrl;
        this.formGenServiceUrl = formGenServiceUrl;
        this.appRepositoryUrl = appRepositoryUrl;
        this.connectionName = connectionName;
    }

    public ProjectDTO(Project project) {
        this.formGenRepositoryUrl = project.getFormGenRepositoryUrl();
        this.formGenServiceUrl = project.getFormGenServiceUrl();
        this.appRepositoryUrl = project.getAppRepositoryUrl();
        this.connectionName = project.getKey();
    }

    public String getFormGenRepositoryUrl() {
        return formGenRepositoryUrl;
    }

    public String getFormGenServiceUrl() {
        return formGenServiceUrl;
    }

    public String getAppRepositoryUrl() {
        return appRepositoryUrl;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setFormGenRepositoryUrl(String formGenRepositoryUrl) {
        this.formGenRepositoryUrl = formGenRepositoryUrl;
    }

    public void setFormGenServiceUrl(String formGenServiceUrl) {
        this.formGenServiceUrl = formGenServiceUrl;
    }

    public void setAppRepositoryUrl(String appRepositoryUrl) {
        this.appRepositoryUrl = appRepositoryUrl;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }
}
