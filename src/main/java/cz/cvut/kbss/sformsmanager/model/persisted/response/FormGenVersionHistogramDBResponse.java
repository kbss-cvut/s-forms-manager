package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.ConstructorResult;
import cz.cvut.kbss.jopa.model.annotations.SparqlResultSetMapping;
import cz.cvut.kbss.jopa.model.annotations.VariableResult;

@SparqlResultSetMapping(name = "FormGenVersionHistogramResults",
        classes = {
                @ConstructorResult(targetClass = FormGenVersionHistogramDBResponse.class, variables = {
                        @VariableResult(name = "version", type = String.class),
                        @VariableResult(name = "year", type = Integer.class),
                        @VariableResult(name = "month", type = Integer.class),
                        @VariableResult(name = "count", type = Integer.class),
                })
        })
public class FormGenVersionHistogramDBResponse {
    private String version;
    private int year;
    private int month;
    private int count;

    public FormGenVersionHistogramDBResponse(String version, Integer year, Integer month, Integer count) {
        this.version = version;
        this.year = year;
        this.month = month;
        this.count = count;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}