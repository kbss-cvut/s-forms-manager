package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.QuestionTemplateSnapshot;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.QuestionTemplateDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.QuestionTemplateSnapshotDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionTemplateService {

    private final QuestionTemplateSnapshotDAO questionTemplateSnapshotDAO;
    private final QuestionTemplateDAO questionTemplateDAO;
    private final FormTemplateService formTemplateService;

    @Autowired
    public QuestionTemplateService(QuestionTemplateSnapshotDAO questionTemplateSnapshotDAO, QuestionTemplateDAO questionTemplateDAO, FormTemplateService formTemplateService) {
        this.questionTemplateSnapshotDAO = questionTemplateSnapshotDAO;
        this.questionTemplateDAO = questionTemplateDAO;
        this.formTemplateService = formTemplateService;
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

    public List<QuestionTemplateSnapshot> findSubQuestionsForOriginPath(String projectName, String questionOriginPath) {
        return questionTemplateSnapshotDAO.findAllWhere(projectName, Vocabulary.p_originPath, questionOriginPath).stream()
                .flatMap(qts -> qts.getQuestionTemplateSnapshots().stream())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<QuestionTemplateSnapshot> findRootQuestions(String projectName) {
        return formTemplateService.findAllVersions(projectName).stream()
                .map(qts -> qts.getQuestionTemplateSnapshot())
                .collect(Collectors.toList());
    }

    public List<QuestionTemplateSnapshot> findAllSnapshots(String projectName) {
        return questionTemplateSnapshotDAO.findAll(projectName);
    }

    public List<QuestionTemplateSnapshot> findAllFormTemplateVersionQuestionSnapshots(String projectName, URI formTemplateVersion) {
        return questionTemplateSnapshotDAO.findAllWhere(projectName, Vocabulary.p_hasFormTemplateVersion, formTemplateVersion);
    }
}
