package cz.cvut.kbss.sformsmanager.model.dto;

import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import lombok.Value;

@Value
public class FormGenMetadataDto {

    private String version;

    public FormGenMetadataDto(FormGenMetadata formGenMetadata) {
        this.version = formGenMetadata.getVersionTag().getVersion();
    }
}
