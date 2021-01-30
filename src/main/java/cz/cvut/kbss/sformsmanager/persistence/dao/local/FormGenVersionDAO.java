package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenVersion;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Optional;

@Component
public class FormGenVersionDAO extends LocalWithConnectionBaseDAO<FormGenVersion> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenVersionDAO.class);

    @Autowired
    protected FormGenVersionDAO(EntityManager em) {
        super(em, FormGenVersion.class);
    }

    public Optional<FormGenVersion> findByVersionName(@NonNull String versionName) {
        try {
            return Optional.of(em.createNativeQuery("SELECT ?x WHERE { ?x ?hasVersionName ?versionName ;a ?type }", type)
                    .setParameter("hasVersionName", URI.create(Vocabulary.p_versionName))
                    .setParameter("versionName", versionName)
                    .setParameter("type", typeUri).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
