package cz.cvut.kbss.sformsmanager.model.dto;


import cz.cvut.kbss.sformsmanager.utils.OWLUtils;

import java.util.Objects;

public final class FormGenRawJson {

    private final String connectionName;
    private final String contextUri;
    private final String rawJson;
    private final String key;

    public FormGenRawJson(String connectionName, String contextUri, String rawJson) {
        this.connectionName = connectionName;
        this.contextUri = contextUri;
        this.rawJson = rawJson;
        this.key = OWLUtils.createInitialsAndConcatWithSlash(connectionName, contextUri);
    }

    public String getConnectionName() {
        return this.connectionName;
    }

    public String getContextUri() {
        return this.contextUri;
    }

    public String getRawJson() {
        return this.rawJson;
    }

    public String getKey() {
        return this.key;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FormGenRawJson)) return false;
        final FormGenRawJson other = (FormGenRawJson) o;
        final Object this$key = this.getKey();
        final Object other$key = other.getKey();
        if (!Objects.equals(this$key, other$key)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $key = this.getKey();
        result = result * PRIME + ($key == null ? 43 : $key.hashCode());
        return result;
    }

    public String toString() {
        return "FormGenRawJson(key=" + this.getKey() + ")";
    }

}
