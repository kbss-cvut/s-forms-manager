package cz.cvut.kbss.sformsmanager.persistence.dao.local.custom;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.response.QuestionSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.service.formgen.LocalFormGenJsonLoader;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class FormGenCacheQuestionDAO extends CustomQueryDAO {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenCacheQuestionDAO.class);

    protected FormGenCacheQuestionDAO(EntityManager em) {
        super(em);
    }

    public QuestionSnapshotRemoteData getQuestionsAndAnswersSnapshot(String projectName, URI contextUri, String rootQuestionOrigin) {
        String contextIdentifier = LocalFormGenJsonLoader.createContextIdentifier(projectName, contextUri);
        return executeOnEntityManagerSingleResult(
                em -> em.createNativeQuery("SELECT ?x WHERE { GRAPH <" + contextIdentifier + "> { ?x ?questionOrigin ?value . } } LIMIT 1", QuestionSnapshotRemoteData.class)
                        .setDescriptor(LocalFormGenJsonLoader.createContextDescriptor(projectName, contextUri)) // does not always work as expected, using in-text specification as well
                        .setParameter("questionOrigin", URI.create(Vocabulary.s_p_has_question_origin))
                        .setParameter("value", URI.create(rootQuestionOrigin))
                        .getSingleResult(),
                "Question&Answer model was not found in the cached formGen context : " + contextIdentifier);
    }
}
