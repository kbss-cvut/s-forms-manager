package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import java.util.Date;

public enum QueryTemplate {
    FORMGEN_VERSION_HASH_QUERY("formGenVersionHash.ftl", String.class),
    FORMGEN_INSTANCE_HASH_QUERY("formGenInstanceHash.ftl", String.class),
    FORMGEN_SAVE_HASH_QUERY("formGenSaveHash.ftl", StringAndDateResponse.class);

    private final String queryName;
    private final Class resultType;

    QueryTemplate(String queryName, Class resultType) {
        this.queryName = queryName;
        this.resultType = resultType;
    }

    public static class StringAndDateResponse {
        private String string;
        private Date date;

        public StringAndDateResponse() {
        }

        public StringAndDateResponse(String string, Date date) {
            this.string = string;
            this.date = date;
        }

        public void setString(String string) {
            this.string = string;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getString() {
            return string;
        }

        public Date getDate() {
            return date;
        }
    }

    public String getQueryName() {
        return queryName;
    }

    public Class getResultType() {
        return resultType;
    }
}
