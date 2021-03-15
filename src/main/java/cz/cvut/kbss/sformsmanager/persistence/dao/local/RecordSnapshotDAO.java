package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.RecordSnapshot;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class RecordSnapshotDAO extends LocalEntityBaseDAO<RecordSnapshot> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RecordSnapshotDAO.class);

    @Autowired
    protected RecordSnapshotDAO(EntityManager em) {
        super(em, RecordSnapshot.class);
    }

    public List<String> findSnapshotRemoteURIs(String projectDescriptorName) {
        try {
            return em.createNativeQuery("SELECT ?sUri WHERE { ?x a ?type . ?x ?hasRemoteURI ?sUri . }", String.class)
                    .setDescriptor(getDescriptorForProject(projectDescriptorName))
                    .setParameter("hasRemoteURI", URI.create(Vocabulary.p_hasRemoteContextURI))
                    .setParameter("type", typeUri)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenceException(String.format("Could not find snapshots URIs. %s", projectDescriptorName), e);
        }
    }

}
