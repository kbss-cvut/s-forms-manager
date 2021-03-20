package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.Map;

public class FormTemplateVersionHistogramDTO {

    private final Map<String, int[]> histogramData;

    private final int earliestYear;

    private final int earliestMonth;

    private final int latestYear;

    private final int latestMonth;

    public FormTemplateVersionHistogramDTO(Map<String, int[]> histogramData, int earliestYear, int earliestMonth, int latestYear, int latestMonth) {
        this.histogramData = histogramData;
        this.earliestYear = earliestYear;
        this.earliestMonth = earliestMonth;
        this.latestYear = latestYear;
        this.latestMonth = latestMonth;
    }

    public Map<String, int[]> getHistogramData() {
        return histogramData;
    }

    public int getEarliestYear() {
        return earliestYear;
    }

    public int getEarliestMonth() {
        return earliestMonth;
    }

    public int getLatestYear() {
        return latestYear;
    }

    public int getLatestMonth() {
        return latestMonth;
    }
}
