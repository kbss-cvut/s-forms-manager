package cz.cvut.kbss.sformsmanager.model.dto;

public final class FormTemplateVersionDTO {

    private final String internalKey;
    private final String internalName;
    private final String internalUri;
    private final String sampleRemoteContextUri;
    private final int numberOfQuestionTemplateSnapshots;
    private final int numberOfRecordSnapshots;

    public FormTemplateVersionDTO(String internalKey, String internalName, String internalUri, String sampleRemoteContextUri, int numberOfQuestionTemplateSnapshots, int numberOfRecordSnapshots) {
        this.internalKey = internalKey;
        this.internalName = internalName;
        this.internalUri = internalUri;
        this.sampleRemoteContextUri = sampleRemoteContextUri;
        this.numberOfQuestionTemplateSnapshots = numberOfQuestionTemplateSnapshots;
        this.numberOfRecordSnapshots = numberOfRecordSnapshots;
    }

    public String getInternalKey() {
        return internalKey;
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

    public int getNumberOfRecordSnapshots() {
        return numberOfRecordSnapshots;
    }
}
