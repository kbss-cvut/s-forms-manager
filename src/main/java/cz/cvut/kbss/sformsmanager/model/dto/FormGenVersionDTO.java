package cz.cvut.kbss.sformsmanager.model.dto;

public final class FormGenVersionDTO {

    private final String versionName;

    private final String internalUri;

    private final String sampleContextUri;

    private final int numberOfInstances;

    private final String synonym;

    public FormGenVersionDTO(String versionName, String internalUri, String sampleContextUri, int numberOfInstances, String synonym) {
        this.versionName = versionName;
        this.internalUri = internalUri;
        this.sampleContextUri = sampleContextUri;
        this.numberOfInstances = numberOfInstances;
        this.synonym = synonym;
    }

    public String getVersionName() {
        return versionName;
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

    public String getSynonym() {
        return synonym;
    }
}
