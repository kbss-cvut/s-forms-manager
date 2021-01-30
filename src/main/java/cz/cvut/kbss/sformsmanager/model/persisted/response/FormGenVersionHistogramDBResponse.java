package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.ConstructorResult;
import cz.cvut.kbss.jopa.model.annotations.SparqlResultSetMapping;
import cz.cvut.kbss.jopa.model.annotations.VariableResult;

@SparqlResultSetMapping(name = "FormGenVersionHistogramResults",
        classes = {
                @ConstructorResult(targetClass = FormGenVersionHistogramDBResponse.class, variables = {
                        @VariableResult(name = "versionName", type = String.class),
                        @VariableResult(name = "year", type = Integer.class),
                        @VariableResult(name = "month", type = Integer.class),
                        @VariableResult(name = "count", type = Integer.class),
                })
        })
public class FormGenVersionHistogramDBResponse {
    private String versionName;
    private int year;
    private int month;
    private int count;

    public FormGenVersionHistogramDBResponse(String versionName, Integer year, Integer month, Integer count) {
        this.versionName = versionName;
        this.year = year;
        this.month = month;
        this.count = count;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
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