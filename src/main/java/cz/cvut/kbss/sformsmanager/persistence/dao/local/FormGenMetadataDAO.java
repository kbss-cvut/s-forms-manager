package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class FormGenMetadataDAO extends LocalWithConnectionBaseDAO<FormGenMetadata> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenMetadataDAO.class);

    @Autowired
    protected FormGenMetadataDAO(EntityManager em) {
        super(em, FormGenMetadata.class);
    }

    public int countAllNonEmptyInConnection(@NonNull String connectionName) {
        try {
            return (int) em.createNativeQuery(
                    "SELECT (count(?x) as ?object) WHERE { ?x ?hasConnectionName ?connectionName ;a ?type . ?x ?saveHash ?b }")
                    .setParameter("hasConnectionName", URI.create(Vocabulary.p_connectionName))
                    .setParameter("connectionName", connectionName)
                    .setParameter("saveHash", URI.create(Vocabulary.p_save_hash))
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }
}
