package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.persistence.dao.local.SubmittedAnswerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmittedAnswerService {

    private final SubmittedAnswerDAO submittedAnswerDAO;

    @Autowired
    public SubmittedAnswerService(SubmittedAnswerDAO submittedAnswerDAO) {
        this.submittedAnswerDAO = submittedAnswerDAO;
    }

    public int count(String projectName) {
        return submittedAnswerDAO.count(projectName);
    }

}
