package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.QuestionTemplate;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionTemplateDAO extends LocalEntityBaseDAO<QuestionTemplate> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QuestionTemplateDAO.class);

    protected QuestionTemplateDAO(EntityManager em) {
        super(em, QuestionTemplate.class);
    }
}
