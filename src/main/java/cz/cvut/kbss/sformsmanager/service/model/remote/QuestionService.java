package cz.cvut.kbss.sformsmanager.service.model.remote;


import cz.cvut.kbss.sformsmanager.model.persisted.remote.Question;
import cz.cvut.kbss.sformsmanager.persistence.dao.remote.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionDAO questionDAO;

    @Autowired
    public QuestionService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public List<Question> findAll(String connectionName, String contextUri) {
        return questionDAO.findAll(connectionName, contextUri);
    }

}
