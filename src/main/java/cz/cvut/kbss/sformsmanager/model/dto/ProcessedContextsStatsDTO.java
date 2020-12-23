package cz.cvut.kbss.sformsmanager.model.dto;

public class ProcessedContextsStatsDTO {

    private int totalContexts;
    private int processedContexts;

    public ProcessedContextsStatsDTO() {
    }

    public ProcessedContextsStatsDTO(int totalContexts, int processedContexts) {
        this.totalContexts = totalContexts;
        this.processedContexts = processedContexts;
    }

    public int getTotalContexts() {
        return totalContexts;
    }

    public void setTotalContexts(int totalContexts) {
        this.totalContexts = totalContexts;
    }

    public int getProcessedContexts() {
        return processedContexts;
    }

    public void setProcessedContexts(int processedContexts) {
        this.processedContexts = processedContexts;
    }
}
