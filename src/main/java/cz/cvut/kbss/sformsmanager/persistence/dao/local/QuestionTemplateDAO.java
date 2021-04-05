package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.QuestionTemplateSnapshot;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionTemplateDAO extends LocalEntityBaseDAO<QuestionTemplateSnapshot> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QuestionTemplateDAO.class);

    @Autowired
    protected QuestionTemplateDAO(EntityManager em) {
        super(em, QuestionTemplateSnapshot.class);
    }
}
