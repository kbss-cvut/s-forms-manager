package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.Record;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class RecordDAO extends LocalEntityBaseDAO<Record> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RecordDAO.class);

    @Autowired
    protected RecordDAO(EntityManager em) {
        super(em, Record.class);
    }

    public List<Record> findAllWithRecordVersion(String projectDescriptorName) {
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x a ?type . ?v a ?typeVersion . ?v ?hasRecord ?x }", type) // TODO: the other way
                    .setDescriptor(getDescriptorForProject(projectDescriptorName))
                    .setParameter("hasRecord", URI.create(Vocabulary.p_hasRecord))
                    .setParameter("type", typeUri)
                    .setParameter("typeVersion", URI.create(Vocabulary.RecordVersion))
                    .getResultList();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }
}
