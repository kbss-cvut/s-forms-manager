package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.FormTemplateVersion;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.QuestionTemplateSnapshot;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class QuestionTemplateSnapshotDAO extends LocalWithConnectionBaseDAO<QuestionTemplateSnapshot> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QuestionTemplateSnapshotDAO.class);

    @Autowired
    protected QuestionTemplateSnapshotDAO(EntityManager em) {
        super(em, QuestionTemplateSnapshot.class);
    }

    public Optional<QuestionTemplateSnapshot> findByVersionName(@NonNull String versionName) {
        try {
            return Optional.of(em.createNativeQuery("SELECT ?x WHERE { ?x ?hasVersionName ?versionName ;a ?type }", type)
                    .setParameter("hasVersionName", URI.create(Vocabulary.p_versionName))
                    .setParameter("versionName", versionName)
                    .setParameter("type", typeUri).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<QuestionTemplateSnapshot> findByVersionNameOrSynonym(@NonNull String versionNameOrSynonym) {
        try {
            return Optional.of(em.createNativeQuery("SELECT ?x WHERE { ?x a ?type . ?x ?hasVersionName|?hasSynonym ?versionOrSynonym . }", type)
                    .setParameter("hasVersionName", URI.create(Vocabulary.p_versionName))
                    .setParameter("hasSynonym", URI.create(Vocabulary.p_synonym))
                    .setParameter("versionOrSynonym", versionNameOrSynonym)
                    .setParameter("type", typeUri).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<QuestionTemplateSnapshot> findByFormTemplateVersionURI(@NonNull FormTemplateVersion formTemplateVersionURI) {
        return em.createNativeQuery("SELECT ?x WHERE { ?x ?hasFormTemplateVersion ?formTemplateVersionURI ;a ?type }", type)
                .setParameter("hasFormTemplateVersion", URI.create(Vocabulary.p_hasFormTemplateVersion))
                .setParameter("formTemplateVersionURI", formTemplateVersionURI)
                .setParameter("type", typeUri).getResultList();
    }
}
