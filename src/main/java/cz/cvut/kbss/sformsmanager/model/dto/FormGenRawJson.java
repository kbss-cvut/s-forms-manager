package cz.cvut.kbss.sformsmanager.model.dto;


public final class FormGenRawJson {

    private final String connectionName;
    private final String contextUri;
    private final String rawJson;

    public FormGenRawJson(String connectionName, String contextUri, String rawJson) {
        this.connectionName = connectionName;
        this.contextUri = contextUri;
        this.rawJson = rawJson;
    }

    public String getConnectionName() {
        return this.connectionName;
    }

    public String getContextUri() {
        return this.contextUri;
    }

    public String getRawJson() {
        return this.rawJson;
    }

}
