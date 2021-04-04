package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.QuestionTemplateDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.QuestionTemplateSnapshotDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class QuestionTemplateService {

    private final QuestionTemplateSnapshotDAO questionTemplateSnapshotDAO;
    private final QuestionTemplateDAO questionTemplateDAO;

    @Autowired
    public QuestionTemplateService(QuestionTemplateSnapshotDAO questionTemplateSnapshotDAO, QuestionTemplateDAO questionTemplateDAO) {
        this.questionTemplateSnapshotDAO = questionTemplateSnapshotDAO;
        this.questionTemplateDAO = questionTemplateDAO;
    }

    public int countQuestionTemplates(String projectName) {
        return questionTemplateDAO.count(projectName);
    }

    public int countQuestionTemplateSnapshots(String projectName) {
        return questionTemplateSnapshotDAO.count(projectName);
    }

    public int countSnapshotsWithFormTemplateVersion(String projectName, URI formTemplateVersionURI) {
        return questionTemplateSnapshotDAO.countWhere(projectName, Vocabulary.p_hasFormTemplateVersion, formTemplateVersionURI);
    }
}
