package cz.cvut.kbss.sformsmanager.persistence.dao.local.custom;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

public class CustomQueryDAO {

    public static final String CUSTOM_QUERY_CONTEXT_URI_PARAM = "contextUri";
    public static final String CLASSPATH_PREFIX = "classpath:";

    protected EntityManager entityManager;

    protected CustomQueryDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> T executeOnEntityManagerSingleResult(EntityManagerSingleResultInvoker<T> emInvoker, String errorMessage) {
        try {
            return emInvoker.accept(entityManager);
        } catch (NoResultException ne) {
            throw new PersistenceException(errorMessage);
        }
    }

    public <T> List<T> executeOnEntityManagerList(EntityManagerToListResultInvoker<T> emInvoker, String errorMessage) {
        try {
            return emInvoker.accept(entityManager);
        } catch (RuntimeException ne) {
            throw new PersistenceException(errorMessage);
        }
    }

    protected String getQueryFromFile(String queryFileName) throws IOException {
        try {
            return new String(Files.readAllBytes(ResourceUtils.getFile(queryFileName).toPath()));
        } catch (IOException e) {
            throw new IOException(String.format("Query from file '%s' could not be found!", queryFileName), e);
        }
    }

    protected URI getProjectContextURI(String projectName) {
        return URI.create(Vocabulary.ProjectContext + "/" + projectName);
    }

    interface EntityManagerSingleResultInvoker<T> {
        T accept(EntityManager entityManager);
    }

    interface EntityManagerToListResultInvoker<T> {
        List<T> accept(EntityManager entityManager);
    }
}
