package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.ConstructorResult;
import cz.cvut.kbss.jopa.model.annotations.SparqlResultSetMapping;
import cz.cvut.kbss.jopa.model.annotations.VariableResult;

import java.util.Date;

@SparqlResultSetMapping(name = "FormGenSaveDBResponseResults",
        classes = {
                @ConstructorResult(targetClass = FormGenSaveDBResponse.class, variables = {
                        @VariableResult(name = "formGenSaveHash", type = String.class),
                        @VariableResult(name = "created", type = Date.class),
                        @VariableResult(name = "modified", type = Date.class)
                })
        })
public class FormGenSaveDBResponse {
    private String formGenSaveHash;
    private Date created;
    private Date modified;

    public FormGenSaveDBResponse() {
    }

    public FormGenSaveDBResponse(String formGenSaveHash, Date created, Date modified) {
        this.formGenSaveHash = formGenSaveHash;
        this.created = created;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}