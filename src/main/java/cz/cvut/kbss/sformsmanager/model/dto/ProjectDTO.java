package cz.cvut.kbss.sformsmanager.model.dto;


import cz.cvut.kbss.sformsmanager.model.persisted.local.Project;

public class ProjectDTO {

    private String formGenRepositoryUrl;
    private String formGenServiceUrl;
    private String appRepositoryUrl;
    private String projectName;

    public ProjectDTO() {
    }

    public ProjectDTO(String formGenRepositoryUrl, String formGenServiceUrl, String appRepositoryUrl, String projectName) {
        this.formGenRepositoryUrl = formGenRepositoryUrl;
        this.formGenServiceUrl = formGenServiceUrl;
        this.appRepositoryUrl = appRepositoryUrl;
        this.projectName = projectName;
    }

    public ProjectDTO(Project project) {
        this.formGenRepositoryUrl = project.getFormGenRepositoryUrl();
        this.formGenServiceUrl = project.getFormGenServiceUrl();
        this.appRepositoryUrl = project.getAppRepositoryUrl();
        this.projectName = project.getKey();
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

    public String getProjectName() {
        return projectName;
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

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
