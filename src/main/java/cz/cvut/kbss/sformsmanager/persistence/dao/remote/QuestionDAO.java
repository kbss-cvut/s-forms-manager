package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.persisted.remote.Question;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectionEntityManagerProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionDAO extends RemoteEntityDAO<Question> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QuestionDAO.class);

    private final ConnectionEntityManagerProvider entityManagerProvider;

    @Autowired
    protected QuestionDAO(ConnectionEntityManagerProvider entityManagerProvider) {
        super(entityManagerProvider, Question.class);
        this.entityManagerProvider = entityManagerProvider;
    }

    public List<Question> findAll(String connectionName, String contextUri) {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);

        try {
            return em.createNativeQuery("SELECT ?x WHERE { graph ?contextUri { ?x a ?type . } }", type)
                    .setParameter("contextUri", "<" + contextUri + ">")
                    .setParameter("type", typeUri)
                    .getResultList();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

//    public String getFormGenVersionHash(String connectionName, String contextUri) {
//        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);
//        try {
//            return em.createNativeQuery("SELECT ?x WHERE { graph ?contextUri { ?x a ?type . } }", type)
//                    .setParameter("contextUri", "<" + contextUri + ">")
//                    .setParameter("type", typeUri)
//                    .getResultList();
//        } catch (RuntimeException e) {
//            log.error(e.getMessage());
//            throw new PersistenceException(e);
//        }
//    }
}
