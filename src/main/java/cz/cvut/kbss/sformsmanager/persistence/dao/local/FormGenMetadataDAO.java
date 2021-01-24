package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenListingElementWithHistory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

import static cz.cvut.kbss.sformsmanager.persistence.dao.remote.RemoteFormGenDAO.CLASSPATH_PREFIX;


@Component
public class FormGenMetadataDAO extends LocalWithConnectionBaseDAO<FormGenMetadata> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenMetadataDAO.class);

    private final String FORMGEN_LISTING_ELEMENT_WITH_HISTORY = CLASSPATH_PREFIX + "templates/local/formListingWithHistory.sparql";


    @Autowired
    protected FormGenMetadataDAO(EntityManager em) {
        super(em, FormGenMetadata.class);
    }

    public int countAllNonEmptyInConnection(@NonNull String connectionName) {
        try {
            return (int) em.createNativeQuery(
                    "SELECT (count(?x) as ?object) WHERE { ?x ?hasConnectionName ?connectionName ;a ?type . ?x ?saveHash ?b }")
                    .setParameter("hasConnectionName", URI.create(Vocabulary.p_connectionName))
                    .setParameter("connectionName", connectionName)
                    .setParameter("saveHash", URI.create(Vocabulary.p_save_hash))
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }

    public List<FormGenListingElementWithHistory> getFormListingWithHistory(String connectionName) throws IOException {
        try {
            String query = new String(Files.readAllBytes(ResourceUtils.getFile(FORMGEN_LISTING_ELEMENT_WITH_HISTORY).toPath()));
            query = query.replace("?connectionName", "<" + URI.create(connectionName) + ">");
            return em.createNativeQuery(query, FormGenListingElementWithHistory.class)
//                    .setParameter("?connectionName", connectionName)
                    .getResultList();

        } catch (IOException e) {
            throw new IOException("Query from file could not be found!", e);
        }
    }
}
