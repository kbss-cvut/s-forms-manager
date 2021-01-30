package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.Date;

public final class FormGenSaveGroupInfoDTO {

    private final String saveHash;

    private final int count;

    private final Date created;

    private final Date lastModified;

    private final String contextUri;

    public FormGenSaveGroupInfoDTO(String saveHash, int count, Date created, Date lastModified, String contextUri) {
        this.saveHash = saveHash;
        this.count = count;
        this.created = created;
        this.lastModified = lastModified;
        this.contextUri = contextUri;
    }

    public String getSaveHash() {
        return saveHash;
    }

    public int getCount() {
        return count;
    }

    public Date getCreated() {
        return created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public String getContextUri() {
        return contextUri;
    }
}
