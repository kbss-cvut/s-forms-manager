package cz.cvut.kbss.sformsmanager.model.dto;


public final class SFormsRawJson {

    private final String projectName;
    private final String contextUri;
    private final String rawJson;

    public SFormsRawJson(String projectName, String contextUri, String rawJson) {
        this.projectName = projectName;
        this.contextUri = contextUri;
        this.rawJson = rawJson;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public String getContextUri() {
        return this.contextUri;
    }

    public String getRawJson() {
        return this.rawJson;
    }

}
