package cz.cvut.kbss.jopa.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;

@Repository
public class ContextRepository {

    @Autowired
    public ContextRepository(EntityManager em) {
        this.em = em;
    }

    private final EntityManager em;

    public List<URI> findAll() {
        return em.getContexts();
    }

}
