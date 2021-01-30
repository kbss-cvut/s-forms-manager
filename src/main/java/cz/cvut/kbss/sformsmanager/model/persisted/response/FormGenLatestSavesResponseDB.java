package cz.cvut.kbss.sformsmanager.model.persisted.response;

import cz.cvut.kbss.jopa.model.annotations.ConstructorResult;
import cz.cvut.kbss.jopa.model.annotations.SparqlResultSetMapping;
import cz.cvut.kbss.jopa.model.annotations.VariableResult;

import java.util.Date;

@SparqlResultSetMapping(name = "FormGenLatestSavesResults",
        classes = {
                @ConstructorResult(targetClass = FormGenLatestSavesResponseDB.class, variables = {
                        @VariableResult(name = "formGenSaveHash", type = String.class),
                        @VariableResult(name = "historySaves", type = Integer.class),
                        @VariableResult(name = "created", type = Date.class),
                        @VariableResult(name = "lastModified", type = Date.class),
                        @VariableResult(name = "lastModifiedContextUri", type = String.class),
                })
        })
public class FormGenLatestSavesResponseDB {

    /**
     * Hash of the latest instance (save) of one form.
     */
    private String formGenSaveHash;

    /**
     * Number of saves over the history.
     */
    private int historySaves;

    private Date created;

    private Date lastModified;

    /**
     * Context URI of formGen of the last save.
     */
    private String lastModifiedContextUri;

    public FormGenLatestSavesResponseDB(String formGenSaveHash, Integer historySaves, Date created, Date lastModified, String lastModifiedContextUri) {
        this.formGenSaveHash = formGenSaveHash;
        this.historySaves = historySaves;
        this.created = created;
        this.lastModified = lastModified;
        this.lastModifiedContextUri = lastModifiedContextUri;
    }

    public String getFormGenSaveHash() {
        return formGenSaveHash;
    }

    public void setFormGenSaveHash(String formGenSaveHash) {
        this.formGenSaveHash = formGenSaveHash;
    }

    public int getHistorySaves() {
        return historySaves;
    }

    public void setHistorySaves(int historySaves) {
        this.historySaves = historySaves;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedContextUri() {
        return lastModifiedContextUri;
    }

    public void setLastModifiedContextUri(String lastModifiedContextUri) {
        this.lastModifiedContextUri = lastModifiedContextUri;
    }
}