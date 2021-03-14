package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.sformsmanager.model.persisted.response.RecordSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectionEntityManagerProvider;
import cz.cvut.kbss.sformsmanager.utils.QueryUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;

@Service
public class RecordSnapshotRemoteDAO extends RemoteEntityDAO {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RecordSnapshotRemoteDAO.class);

    public static final String CLASSPATH_PREFIX = "classpath:";
    private static final String FORMGEN_SAVE_QUERY_FILE = CLASSPATH_PREFIX + "templates/remote/recordSnapshotRemoteData.sparql";

    @Autowired
    protected RecordSnapshotRemoteDAO(ConnectionEntityManagerProvider entityManagerProvider) {
        super(entityManagerProvider, RecordSnapshotRemoteDAO.class);
    }

    public RecordSnapshotRemoteData getRecordSnapshot(String connectionName, URI contextUri) throws IOException {
        return (RecordSnapshotRemoteData) executeOnEntityManager(connectionName,
                em -> {
                    String query = QueryUtils.getQueryFromFile(FORMGEN_SAVE_QUERY_FILE);
                    return em.createNativeQuery(query, "RecordSnapshotRemoteDataResponse")
                            .setParameter("contextUri", contextUri)
                            .getSingleResult();
                },
                "RecordSnapshotIntermediate formGen context not found: " + contextUri.toString());
    }
}
