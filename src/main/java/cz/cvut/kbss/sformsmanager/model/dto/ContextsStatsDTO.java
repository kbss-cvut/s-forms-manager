package cz.cvut.kbss.sformsmanager.model.dto;

public class ContextsStatsDTO {

    private int totalContexts;
    private int processedContexts;

    public ContextsStatsDTO() {
    }

    public ContextsStatsDTO(int totalContexts, int processedContexts) {
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
