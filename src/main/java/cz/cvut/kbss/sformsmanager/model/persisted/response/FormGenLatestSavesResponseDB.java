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
                        @VariableResult(name = "lastSaved", type = Date.class),
                        @VariableResult(name = "lastSavedContextUri", type = String.class),
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

    /**
     * Date of latest save.
     */
    private Date lastSaved;

    /**
     * Context URI of formGen of the last save.
     */
    private String lastSavedContextUri;

    public FormGenLatestSavesResponseDB(String formGenSaveHash, Integer historySaves, Date lastSaved, String lastSavedContextUri) {
        this.formGenSaveHash = formGenSaveHash;
        this.historySaves = historySaves;
        this.lastSaved = lastSaved;
        this.lastSavedContextUri = lastSavedContextUri;
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

    public Date getLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(Date lastSaved) {
        this.lastSaved = lastSaved;
    }

    public String getLastSavedContextUri() {
        return lastSavedContextUri;
    }

    public void setLastSavedContextUri(String lastSavedContextUri) {
        this.lastSavedContextUri = lastSavedContextUri;
    }
}