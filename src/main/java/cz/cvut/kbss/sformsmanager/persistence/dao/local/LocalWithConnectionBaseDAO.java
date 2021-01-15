package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.HasConnection;
import cz.cvut.kbss.sformsmanager.model.persisted.HasUniqueKey;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;
import org.slf4j.Logger;
import org.springframework.lang.NonNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public abstract class LocalWithConnectionBaseDAO<T extends LocalEntity & HasUniqueKey & HasConnection> extends LocalEntityBaseDAO<T> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocalWithConnectionBaseDAO.class);

    protected final EntityManager em;

    protected LocalWithConnectionBaseDAO(EntityManager em, Class<T> type) {
        super(em, type);
        this.em = em;
    }

    public List<T> findAllPaginatedInConnection(@NonNull String connectionName, int offset, int limit) {
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x ?hasConnectionName ?connectionName ;a ?type } OFFSET ?offset LIMIT ?limit", type)
                    .setParameter("hasConnectionName", URI.create(Vocabulary.p_connectionName))
                    .setParameter("connectionName", connectionName)
                    .setParameter("offset", offset)
                    .setParameter("limit", limit)
                    .setParameter("type", typeUri).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List<T> findAllInConnection(@NonNull String connectionName) {
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x ?hasConnectionName ?connectionName ;a ?type }", type)
                    .setParameter("hasConnectionName", URI.create(Vocabulary.p_connectionName))
                    .setParameter("connectionName", connectionName)
                    .setParameter("type", typeUri).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public int countAllInConnection(@NonNull String connectionName) {
        try {
            return (int) em.createNativeQuery(
                    "SELECT (count(?x) as ?object) WHERE { ?x ?hasConnectionName ?connectionName ;a ?type }")
                    .setParameter("hasConnectionName", URI.create(Vocabulary.p_connectionName))
                    .setParameter("connectionName", connectionName)
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }

    public int countAllInConnectionByVersion(@NonNull String connectionName, @NonNull String version) {
        try {
            return (int) em.createNativeQuery(
                    "SELECT (count(?x) as ?object) WHERE { ?x ?hasConnectionName ?connectionName ;a ?type ;?hasVersion ?versionParam  }")
                    .setParameter("hasConnectionName", URI.create(Vocabulary.p_connectionName))
                    .setParameter("connectionName", connectionName)
                    .setParameter("hasVersion", URI.create(Vocabulary.p_assigned_version))
                    .setParameter("versionParam", URI.create(version))
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }

}