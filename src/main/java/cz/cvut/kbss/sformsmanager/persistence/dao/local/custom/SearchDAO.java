package cz.cvut.kbss.sformsmanager.persistence.dao.local.custom;

import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchDAO extends CustomQueryDAO {

    @Autowired
    protected SearchDAO(EntityManager em) {
        super(em);
    }

    public List<Object> runSearchQuery(String searchQuery) {
        return executeOnEntityManagerList(
                em -> em.createNativeQuery(searchQuery)
                        .getResultList(),
                "Could not run custom search query.");

    }
}

