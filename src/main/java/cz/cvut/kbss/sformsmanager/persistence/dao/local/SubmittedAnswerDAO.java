package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.SubmittedAnswer;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmittedAnswerDAO extends LocalEntityBaseDAO<SubmittedAnswer> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SubmittedAnswerDAO.class);

    @Autowired
    protected SubmittedAnswerDAO(EntityManager em) {
        super(em, SubmittedAnswer.class);
    }
}
