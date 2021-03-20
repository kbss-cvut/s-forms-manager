package cz.cvut.kbss.sformsmanager.model.dto;

public final class FormTemplateVersionDTO {

    private final String key;
    private final String internalName;
    private final String internalUri;
    private final String sampleRemoteContextUri;
    private final int numberOfQuestionTemplateSnapshots;

    public FormTemplateVersionDTO(String key, String internalName, String internalUri, String sampleRemoteContextUri, int numberOfQuestionTemplateSnapshots) {
        this.key = key;
        this.internalName = internalName;
        this.internalUri = internalUri;
        this.sampleRemoteContextUri = sampleRemoteContextUri;
        this.numberOfQuestionTemplateSnapshots = numberOfQuestionTemplateSnapshots;
    }

    public String getKey() {
        return key;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getInternalUri() {
        return internalUri;
    }

    public String getSampleRemoteContextUri() {
        return sampleRemoteContextUri;
    }

    public int getNumberOfQuestionTemplateSnapshots() {
        return numberOfQuestionTemplateSnapshots;
    }
}
