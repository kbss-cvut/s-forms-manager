package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenLatestAndNewestDateDBResponse;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenLatestSavesResponseDB;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenVersionHistogramDBResponse;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
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
public class FormGenMetadataDAO extends LocalEntityBaseDAO<FormGenMetadata> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenMetadataDAO.class);

    private final String FORMGEN_LISTING_ELEMENT_WITH_HISTORY_FILE = CLASSPATH_PREFIX + "templates/local/formListingWithHistory.sparql";
    private final String FORMGEN_OLDEST_LATEST_DATE_FILE = CLASSPATH_PREFIX + "templates/local/newestAndOldestFormGen.sparql";
    private final String FORMGEN_VERSION_HISTOGRAM_FILE = CLASSPATH_PREFIX + "templates/local/versionHistogram.sparql";


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

    public List<FormGenLatestSavesResponseDB> getFormListingWithHistory(String connectionName) throws IOException {
        try {
            String query = new String(Files.readAllBytes(ResourceUtils.getFile(FORMGEN_LISTING_ELEMENT_WITH_HISTORY_FILE).toPath()));
            return em.createNativeQuery(query, "FormGenLatestSavesResults")
                    .setParameter("connectionName", connectionName)
                    .getResultList();

        } catch (IOException e) {
            throw new IOException("Query from file could not be found!", e);
        }
    }

    public FormGenLatestAndNewestDateDBResponse getOldestAndLatestDate(String connectionName) throws IOException {
        try {
            String query = new String(Files.readAllBytes(ResourceUtils.getFile(FORMGEN_OLDEST_LATEST_DATE_FILE).toPath()));
            return (FormGenLatestAndNewestDateDBResponse) em.createNativeQuery(query, "FormGenLatestAndNewestDateResults")
                    .setParameter("connectionName", connectionName)
                    .getSingleResult();

        } catch (IOException e) {
            throw new IOException("Query from file could not be found!", e);
        }
    }

    public List<FormGenVersionHistogramDBResponse> getVersionHistogramDataByConnectionName(String connectionName) throws IOException {
        try {
            String query = new String(Files.readAllBytes(ResourceUtils.getFile(FORMGEN_VERSION_HISTOGRAM_FILE).toPath()));
            return (List<FormGenVersionHistogramDBResponse>) em.createNativeQuery(query, "FormGenVersionHistogramResults")
                    .setParameter("connectionName", connectionName)
                    .getResultList();

        } catch (IOException e) {
            throw new IOException("Query from file could not be found!", e);
        }
    }

    public List<FormGenMetadata> findAllWithSaveHash(@NonNull String connectionName, @NonNull String saveHash) {
        try {
            return em.createNativeQuery(
                    "SELECT ?x WHERE { ?x a ?type ;?hasSaveHash ?saveHash }", FormGenMetadata.class)
                    .setParameter("hasSaveHash", URI.create(Vocabulary.p_save_hash))
                    .setParameter("saveHash", saveHash)
                    .setParameter("type", typeUri).getResultList();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Couldn't find history for " + typeUri.toString() + ".", e);
        }
    }

    public List<FormGenMetadata> runSearchQuery(String searchQuery) {
        try {
            return em.createNativeQuery(searchQuery, FormGenMetadata.class).getResultList();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Couldn't run search query: " + searchQuery, e);
        }
    }
}
