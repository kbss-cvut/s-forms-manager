package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.sformsmanager.model.persisted.response.QuestionSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.persistence.base.RemoteProjectEntityManagerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;

@Service
public class QuestionTemplateSnapshotRemoteDAO extends RemoteEntityDAO<QuestionSnapshotRemoteData> {

    @Autowired
    protected QuestionTemplateSnapshotRemoteDAO(RemoteProjectEntityManagerProvider entityManagerProvider) {
        super(entityManagerProvider, QuestionSnapshotRemoteData.class);
    }

    public QuestionSnapshotRemoteData getQuestionsAndAnswersSnapshot(String projectName, URI contextUri, URI question) throws IOException {
        return executeOnEntityManager(projectName,
                em -> em.find(QuestionSnapshotRemoteData.class, question),
                "Question&Answer model was not found in : " + contextUri.toString());
    }
}
