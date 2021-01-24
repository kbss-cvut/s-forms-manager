package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.ConstructorResult;
import cz.cvut.kbss.jopa.model.annotations.SparqlResultSetMapping;
import cz.cvut.kbss.jopa.model.annotations.VariableResult;

import java.util.Date;

@SparqlResultSetMapping(name = "FormGenSaveDBResponseResults",
        classes = {
                @ConstructorResult(targetClass = FormGenSaveDBResponse.class, variables = {
                        @VariableResult(name = "formGenSaveHash", type = String.class),
                        @VariableResult(name = "modified", type = Date.class)
                })
        })
public class FormGenSaveDBResponse {
    private String formGenSaveHash;
    private Date modified;

    public FormGenSaveDBResponse() {
    }

    public FormGenSaveDBResponse(String formGenSaveHash, Date modified) {
        this.formGenSaveHash = formGenSaveHash;
        this.modified = modified;
    }

    public void setFormGenSaveHash(String formGenSaveHash) {
        this.formGenSaveHash = formGenSaveHash;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getFormGenSaveHash() {
        return formGenSaveHash;
    }

    public Date getModified() {
        return modified;
    }
}