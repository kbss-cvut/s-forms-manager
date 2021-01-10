package cz.cvut.kbss.sformsmanager.model.dto;

import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;

public final class FormGenMetadataDTO {

    private final String version;

    private final String instance;

    public FormGenMetadataDTO(FormGenMetadata formGenMetadata) {
        this.version = formGenMetadata.getFormGenVersion().getVersion();
        this.instance = formGenMetadata.getFormGenInstance().getUri().toString();
    }

    public String getVersion() {
        return this.version;
    }

    public String getInstance() {
        return instance;
    }
}
