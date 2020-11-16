package cz.cvut.kbss.sformsmanager.model.dto;

import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;

public final class FormGenMetadataDto {

    private final String version;

    public FormGenMetadataDto(FormGenMetadata formGenMetadata) {
        this.version = formGenMetadata.getVersionTag().getVersion();
    }

    public String getVersion() {
        return this.version;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FormGenMetadataDto)) return false;
        final FormGenMetadataDto other = (FormGenMetadataDto) o;
        final Object this$version = this.getVersion();
        final Object other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $version = this.getVersion();
        result = result * PRIME + ($version == null ? 43 : $version.hashCode());
        return result;
    }

    public String toString() {
        return "FormGenMetadataDto(version=" + this.getVersion() + ")";
    }
}
