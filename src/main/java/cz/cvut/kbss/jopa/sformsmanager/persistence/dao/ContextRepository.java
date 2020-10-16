package cz.cvut.kbss.jopa.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContextRepository {

    private final EntityManager em;

    public List<URI> findAll() {
        return em.getContexts();
    }

}
