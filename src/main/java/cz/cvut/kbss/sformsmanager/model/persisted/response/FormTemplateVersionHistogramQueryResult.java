package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.ConstructorResult;
import cz.cvut.kbss.jopa.model.annotations.SparqlResultSetMapping;
import cz.cvut.kbss.jopa.model.annotations.VariableResult;

@SparqlResultSetMapping(name = FormTemplateVersionHistogramQueryResult.HISTOGRAM_MAPPING,
        classes = {
                @ConstructorResult(targetClass = FormTemplateVersionHistogramQueryResult.class, variables = {
                        @VariableResult(name = "versionKeyOrInternalName", type = String.class),
                        @VariableResult(name = "year", type = Integer.class),
                        @VariableResult(name = "month", type = Integer.class),
                        @VariableResult(name = "count", type = Integer.class),
                })
        })
public class FormTemplateVersionHistogramQueryResult {
    public static final String HISTOGRAM_MAPPING = "FormTemplateVersionHistogramQueryMapping";

    private String versionKeyOrInternalName;
    private int year;
    private int month;
    private int count;

    public FormTemplateVersionHistogramQueryResult(String versionKeyOrInternalName, Integer year, Integer month, Integer count) {
        this.versionKeyOrInternalName = versionKeyOrInternalName;
        this.year = year;
        this.month = month;
        this.count = count;
    }

    public String getVersionName() {
        return versionKeyOrInternalName;
    }

    public void setVersionName(String versionKeyOrInternalName) {
        this.versionKeyOrInternalName = versionKeyOrInternalName;
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