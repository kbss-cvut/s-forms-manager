package cz.cvut.kbss.sformsmanager.model.dto;


public final class FormGenRawJson {

    private final String connectionName;
    private final String contextUri;
    private final String rawJson;
    private final String key;

    public FormGenRawJson(String connectionName, String contextUri, String rawJson, String key) {
        this.connectionName = connectionName;
        this.contextUri = contextUri;
        this.rawJson = rawJson;
        this.key = key;
    }

    public static FormGenRawJsonBuilder builder() {
        return new FormGenRawJsonBuilder();
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
        if (this$key == null ? other$key != null : !this$key.equals(other$key)) return false;
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

    public static class FormGenRawJsonBuilder {
        private String connectionName;
        private String contextUri;
        private String rawJson;
        private String key;

        FormGenRawJsonBuilder() {
        }

        public FormGenRawJson.FormGenRawJsonBuilder connectionName(String connectionName) {
            this.connectionName = connectionName;
            return this;
        }

        public FormGenRawJson.FormGenRawJsonBuilder contextUri(String contextUri) {
            this.contextUri = contextUri;
            return this;
        }

        public FormGenRawJson.FormGenRawJsonBuilder rawJson(String rawJson) {
            this.rawJson = rawJson;
            return this;
        }

        public FormGenRawJson.FormGenRawJsonBuilder key(String key) {
            this.key = key;
            return this;
        }

        public FormGenRawJson build() {
            return new FormGenRawJson(connectionName, contextUri, rawJson, key);
        }

        public String toString() {
            return "FormGenRawJson.FormGenRawJsonBuilder(connectionName=" + this.connectionName + ", contextUri=" + this.contextUri + ", rawJson=" + this.rawJson + ", key=" + this.key + ")";
        }
    }
}
