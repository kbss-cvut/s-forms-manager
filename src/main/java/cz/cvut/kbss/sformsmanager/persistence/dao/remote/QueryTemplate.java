package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.sformsmanager.persistence.dao.response.StringAndDateResponse;
import cz.cvut.kbss.sformsmanager.persistence.dao.response.StringIntDateStringResponse;

public enum QueryTemplate {
    REMOTE_FORMGEN_VERSION_HASH_QUERY("remote/formGenVersionHash.ftl", String.class),
    REMOTE_FORMGEN_INSTANCE_HASH_QUERY("remote/formGenInstanceHash.ftl", String.class),
    REMOTE_FORMGEN_SAVE_HASH_QUERY("remote/formGenSaveHash.ftl", StringAndDateResponse.class),
    LOCAL_FORMGEN_SAVE_HASH_QUERY("local/latestFormGensWithHistoryCount.ftl", StringIntDateStringResponse.class);

    private final String queryName;
    private final Class resultType;

    QueryTemplate(String queryName, Class resultType) {
        this.queryName = queryName;
        this.resultType = resultType;
    }

    public String getQueryName() {
        return queryName;
    }

    public Class getResultType() {
        return resultType;
    }
}
