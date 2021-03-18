package cz.cvut.kbss.sformsmanager.model.dto;

public final class FormTemplateVersionDTO {

    private final String key;
    private final String internalName;
    private final String internalUri;
    private final String sampleRemoteContextUri;

//    private final int numberOfQuestionTemplateSnapshots;
//    private final int numberOfRecordSnapshots;

    public FormTemplateVersionDTO(String key, String internalName, String internalUri, String sampleRemoteContextUri) {
        this.key = key;
        this.internalName = internalName;
        this.internalUri = internalUri;
        this.sampleRemoteContextUri = sampleRemoteContextUri;
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
}
