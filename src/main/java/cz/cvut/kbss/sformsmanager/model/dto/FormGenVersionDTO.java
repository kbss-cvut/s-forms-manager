package cz.cvut.kbss.sformsmanager.model.dto;

public final class FormGenVersionDTO {

    private final String version;

    private final String internalUri;

    private final String sampleContextUri;

    private final int numberOfInstances;

    public FormGenVersionDTO(String version, String internalUri, String sampleContextUri, int numberOfInstances) {
        this.version = version;
        this.internalUri = internalUri;
        this.sampleContextUri = sampleContextUri;
        this.numberOfInstances = numberOfInstances;
    }

    public String getVersion() {
        return version;
    }

    public String getInternalUri() {
        return internalUri;
    }

    public String getSampleContextUri() {
        return sampleContextUri;
    }

    public int getNumberOfInstances() {
        return numberOfInstances;
    }
}
