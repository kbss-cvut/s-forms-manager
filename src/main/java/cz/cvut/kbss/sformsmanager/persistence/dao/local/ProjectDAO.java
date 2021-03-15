package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.persisted.local.Project;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectDAO extends LocalEntityBaseDAO<Project> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProjectDAO.class);

    @Autowired
    protected ProjectDAO(EntityManager em) {
        super(em, Project.class);
    }

    /**
     * Find all Project(s) without specifying context.
     *
     * @return
     */
    public List<Project> findAll() {
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x a ?type . }", type)
                    .setParameter("type", typeUri)
                    .getResultList();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }
}
