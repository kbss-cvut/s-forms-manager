package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.Date;

public final class FormGenSaveGroupInfoDTO {

    private final String saveHash;

    private final int count;

    private final Date lastSaved;

    private final String contextUri;

    public FormGenSaveGroupInfoDTO(String saveHash, int count, Date lastSaved, String contextUri) {
        this.saveHash = saveHash;
        this.count = count;
        this.lastSaved = lastSaved;
        this.contextUri = contextUri;
    }

    public String getSaveHash() {
        return saveHash;
    }

    public int getCount() {
        return count;
    }

    public Date getLastSaved() {
        return lastSaved;
    }

    public String getContextUri() {
        return contextUri;
    }
}
