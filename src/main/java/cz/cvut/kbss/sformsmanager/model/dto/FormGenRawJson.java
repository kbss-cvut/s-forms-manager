package cz.cvut.kbss.sformsmanager.model.dto;


import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

public final class FormGenRawJson {

    private final String connectionName;
    private final String contextUri;
    private final String rawJson;
    private final String key;

    public FormGenRawJson(String connectionName, String contextUri, String rawJson) {
        this.connectionName = connectionName;
        this.contextUri = contextUri;
        this.rawJson = rawJson;
        this.key = OWLUtils.createInitialsAndConcatWithSlash(connectionName, contextUri);
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

    public String getKey() {
        return this.key;
    }

}
