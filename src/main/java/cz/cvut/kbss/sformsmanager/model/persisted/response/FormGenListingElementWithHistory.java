package cz.cvut.kbss.sformsmanager.model.persisted.response;

import java.util.Date;

public class FormGenListingElementWithHistory {
    private String formGenSaveHash;
    private int historySaves;
    private Date lastSaved;
    private String lastSavedContextUri;

    public FormGenListingElementWithHistory(String formGenSaveHash, int historySaves, Date lastSaved, String lastSavedContextUri) {
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