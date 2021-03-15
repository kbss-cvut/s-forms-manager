package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.LocalEntity;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class PaginatedDAO<T extends LocalEntity> extends LocalEntityBaseDAO<T> {

    protected final EntityManager em;

    protected PaginatedDAO(EntityManager em, Class<T> type) {
        super(em, type);
        this.em = em;
    }

    public List<T> findAllPaginatedInConnection(int offset, int limit) {
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x a ?type } OFFSET ?offset LIMIT ?limit", type)
                    .setParameter("hasConnectionName", URI.create(Vocabulary.p_connectionName))
                    .setParameter("offset", offset)
                    .setParameter("limit", limit)
                    .setParameter("type", typeUri).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}
