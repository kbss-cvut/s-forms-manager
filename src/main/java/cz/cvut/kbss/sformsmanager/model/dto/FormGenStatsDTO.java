package cz.cvut.kbss.sformsmanager.model.dto;

public class FormGenStatsDTO {

    private int totalContexts;
    private int processedContexts;
    private int recognizedVersions;
    private int recognizedInstaces;
    private int nonEmptyContexts;

    public FormGenStatsDTO() {
    }

    public FormGenStatsDTO(int totalContexts, int processedContexts, int recognizedVersions, int recognizedInstances, int nonEmptyContexts) {
        this.totalContexts = totalContexts;
        this.processedContexts = processedContexts;
        this.recognizedVersions = recognizedVersions;
        this.recognizedInstaces = recognizedInstances;
        this.nonEmptyContexts = nonEmptyContexts;
    }

    public int getProcessedContexts() {
        return processedContexts;
    }

    public void setProcessedContexts(int processedContexts) {
        this.processedContexts = processedContexts;
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

    public int getTotalContexts() {
        return totalContexts;
    }

    public void setTotalContexts(int totalContexts) {
        this.totalContexts = totalContexts;
    }

    public int getNonEmptyContexts() {
        return nonEmptyContexts;
    }

    public void setNonEmptyContexts(int nonEmptyContexts) {
        this.nonEmptyContexts = nonEmptyContexts;
    }
}
