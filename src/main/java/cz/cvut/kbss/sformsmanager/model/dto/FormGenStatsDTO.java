package cz.cvut.kbss.sformsmanager.model.dto;

public class FormGenStatsDTO {

    private int formUploads;
    private int recognizedVersions;
    private int recognizedInstaces;

    public FormGenStatsDTO() {
    }

    public FormGenStatsDTO(int formUploads, int recognizedVersions, int recognizedInstaces) {
        this.formUploads = formUploads;
        this.recognizedVersions = recognizedVersions;
        this.recognizedInstaces = recognizedInstaces;
    }

    public int getFormUploads() {
        return formUploads;
    }

    public void setFormUploads(int formUploads) {
        this.formUploads = formUploads;
    }

    public int getRecognizedVersions() {
        return recognizedVersions;
    }

    public void setRecognizedVersions(int recognizedVersions) {
        this.recognizedVersions = recognizedVersions;
    }

    public int getRecognizedInstaces() {
        return recognizedInstaces;
    }

    public void setRecognizedInstaces(int recognizedInstaces) {
        this.recognizedInstaces = recognizedInstaces;
    }
}
