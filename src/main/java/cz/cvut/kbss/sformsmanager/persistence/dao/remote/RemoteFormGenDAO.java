package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenLatestSavesResponseDB;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectionEntityManagerProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;

@Service
public class RemoteFormGenDAO extends RemoteEntityDAO {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RemoteFormGenDAO.class);

    public static final String CLASSPATH_PREFIX = "classpath:";
    private static final String FORMGEN_VERSION_QUERY_FILE = CLASSPATH_PREFIX + "templates/remote/formGenVersionHash.sparql";
    private static final String FORMGEN_SAVE_QUERY_FILE = CLASSPATH_PREFIX + "templates/remote/formGenSaveHash.sparql";
    private static final String FORMGEN_INSTANCE_QUERY_FILE = CLASSPATH_PREFIX + "templates/remote/formGenInstanceHash.sparql";

    @Autowired
    protected RemoteFormGenDAO(ConnectionEntityManagerProvider entityManagerProvider) {
        super(entityManagerProvider, RemoteFormGenDAO.class);
    }

    public FormGenLatestSavesResponseDB getFormGenSaveIdentifier(String connectionName, String contextUri) throws IOException {
        EntityManager em = getEntityManagerByConnection(connectionName);
        try {
            String query = new String(Files.readAllBytes(ResourceUtils.getFile(FORMGEN_SAVE_QUERY_FILE).toPath()));
            return (FormGenLatestSavesResponseDB) em.createNativeQuery(query, "FormGenSaveDBResponseResults")
                    .setParameter("contextUri", URI.create(contextUri))
                    .getSingleResult();

        } catch (IOException e) {
            throw new IOException("Query from file could not be found!", e);
        }
    }

    public String getFormGenInstanceIdentifier(String connectionName, String contextUri) throws IOException {
        EntityManager em = getEntityManagerByConnection(connectionName);
        try {
            String query = new String(Files.readAllBytes(ResourceUtils.getFile(FORMGEN_INSTANCE_QUERY_FILE).toPath()));
            return em.createNativeQuery(query, String.class)
                    .setParameter("contextUri", URI.create(contextUri))
                    .getSingleResult();

        } catch (IOException e) {
            throw new IOException("Query from file could not be found!", e);
        }
    }

    public String getFormGenVersionIdentifier(String connectionName, String contextUri) throws IOException {
        EntityManager em = getEntityManagerByConnection(connectionName);
        try {
            String query = new String(Files.readAllBytes(ResourceUtils.getFile(FORMGEN_VERSION_QUERY_FILE).toPath()));
            return em.createNativeQuery(query, String.class)
                    .setParameter("contextUri", URI.create(contextUri))
                    .getSingleResult();

        } catch (IOException e) {
            throw new IOException("Query from file could not be found!", e);
        }
    }
}
