package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.RecordSnapshot;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.custom.CustomQueryDAO;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;

@Repository
public class RecordSnapshotDAO extends LocalEntityBaseDAO<RecordSnapshot> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RecordSnapshotDAO.class);

    protected RecordSnapshotDAO(EntityManager em) {
        super(em, RecordSnapshot.class);
    }

    public List<String> findSnapshotRemoteURIs(String projectDescriptorName) {
        try {
            return em.createNativeQuery("SELECT ?sUri WHERE { GRAPH ?contextUri { ?x a ?type . ?x ?hasRemoteURI ?sUri . } }", String.class)
                    .setParameter(CustomQueryDAO.CUSTOM_QUERY_CONTEXT_URI_PARAM, getProjectContextURI(projectDescriptorName))
                    .setParameter("hasRemoteURI", URI.create(Vocabulary.p_hasRemoteContextURI))
                    .setParameter("type", typeUri)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenceException(String.format("Could not find snapshots URIs. %s", projectDescriptorName), e);
        }
    }

    public int countAllWithFormTemplateVersion(String projectDescriptorName) {
        try {
            return em.createNativeQuery("SELECT (COUNT(?x) as ?count) WHERE { GRAPH ?contextUri { ?x a ?type . ?x ?hasFormTemplateVersion ?whatever . } }", Integer.class)
                    .setParameter(CustomQueryDAO.CUSTOM_QUERY_CONTEXT_URI_PARAM, getProjectContextURI(projectDescriptorName))
                    .setParameter("hasFormTemplateVersion", URI.create(Vocabulary.p_hasFormTemplateVersion))
                    .setParameter("type", typeUri)
                    .getSingleResult();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }
}
