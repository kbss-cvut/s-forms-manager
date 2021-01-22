package cz.cvut.kbss.sformsmanager.model.dto;

import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;

public final class FormGenMetadataDTO {

    private final String version;

    private final String instance;

    private final String created;

    private final String contextUri;

    private final String saveHash;

    public FormGenMetadataDTO(FormGenMetadata formGenMetadata) {
        this.version = formGenMetadata.getFormGenVersion().getVersion();
        this.instance = formGenMetadata.getFormGenInstance().getUri().toString();
        this.created = formGenMetadata.getFormGenCreated().toString();
        this.contextUri = formGenMetadata.getContextUri();
        this.saveHash = formGenMetadata.getFormGenSaveHash();
    }

    public String getVersion() {
        return this.version;
    }

    public String getInstance() {
        return instance;
    }

    public String getCreated() {
        return created;
    }

    public String getContextUri() {
        return contextUri;
    }

    public String getSaveHash() {
        return saveHash;
    }
}
