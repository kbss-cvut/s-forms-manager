package cz.cvut.kbss.sformsmanager.model.dto;


import cz.cvut.kbss.sformsmanager.model.persisted.local.Connection;

public class ConnectionDTO {

    private String formGenRepositoryUrl;
    private String formGenServiceUrl;
    private String appRepositoryUrl;
    private String connectionName;

    public ConnectionDTO() {
    }

    public ConnectionDTO(String formGenRepositoryUrl, String formGenServiceUrl, String appRepositoryUrl, String connectionName) {
        this.formGenRepositoryUrl = formGenRepositoryUrl;
        this.formGenServiceUrl = formGenServiceUrl;
        this.appRepositoryUrl = appRepositoryUrl;
        this.connectionName = connectionName;
    }

    public ConnectionDTO(Connection connection) {
        this.formGenRepositoryUrl = connection.getFormGenRepositoryUrl();
        this.formGenServiceUrl = connection.getFormGenServiceUrl();
        this.appRepositoryUrl = connection.getAppRepositoryUrl();
        this.connectionName = connection.getKey();
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
