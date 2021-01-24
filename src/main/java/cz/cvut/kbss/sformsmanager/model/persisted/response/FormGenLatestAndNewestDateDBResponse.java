package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.ConstructorResult;
import cz.cvut.kbss.jopa.model.annotations.SparqlResultSetMapping;
import cz.cvut.kbss.jopa.model.annotations.VariableResult;

@SparqlResultSetMapping(name = "FormGenLatestAndNewestDateResults",
        classes = {
                @ConstructorResult(targetClass = FormGenLatestAndNewestDateDBResponse.class, variables = {
                        @VariableResult(name = "earliestYear", type = Integer.class),
                        @VariableResult(name = "earliestMonth", type = Integer.class),
                        @VariableResult(name = "latestYear", type = Integer.class),
                        @VariableResult(name = "latestMonth", type = Integer.class),
                })
        })
public class FormGenLatestAndNewestDateDBResponse {
    private int earliestYear;
    private int earliestMonth;
    private int latestYear;
    private int latestMonth;

    public FormGenLatestAndNewestDateDBResponse(Integer earliestYear, Integer earliestMonth, Integer latestYear, Integer latestMonth) {
        this.earliestYear = earliestYear;
        this.earliestMonth = earliestMonth;
        this.latestYear = latestYear;
        this.latestMonth = latestMonth;
    }

    public int getEarliestYear() {
        return earliestYear;
    }

    public void setEarliestYear(int earliestYear) {
        this.earliestYear = earliestYear;
    }

    public int getEarliestMonth() {
        return earliestMonth;
    }

    public void setEarliestMonth(int earliestMonth) {
        this.earliestMonth = earliestMonth;
    }

    public int getLatestYear() {
        return latestYear;
    }

    public void setLatestYear(int latestYear) {
        this.latestYear = latestYear;
    }

    public int getLatestMonth() {
        return latestMonth;
    }

    public void setLatestMonth(int latestMonth) {
        this.latestMonth = latestMonth;
    }
}