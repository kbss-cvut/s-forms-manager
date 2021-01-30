package cz.cvut.kbss.sformsmanager.model.dto;

import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;

import java.util.Date;

public final class FormGenMetadataDTO {

    private final String versionName;

    private final String synonym;

    private final String instance;

    private final Date created;

    private final Date modified;

    private final String contextUri;

    private final String saveHash;

    public FormGenMetadataDTO(FormGenMetadata formGenMetadata) {
        this.versionName = formGenMetadata.getFormGenVersion().getVersionName();
        this.synonym = formGenMetadata.getFormGenVersion().getSynonym();
        this.instance = formGenMetadata.getFormGenInstance().getUri().toString();
        this.created = formGenMetadata.getFormGenCreated();
        this.modified = formGenMetadata.getFormGenModified();
        this.contextUri = formGenMetadata.getContextUri();
        this.saveHash = formGenMetadata.getFormGenSaveHash();
    }

    public String getVersionName() {
        return this.versionName;
    }

    public String getInstance() {
        return instance;
    }

    public String getContextUri() {
        return contextUri;
    }

    public String getSaveHash() {
        return saveHash;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    public String getSynonym() {
        return synonym;
    }
}
