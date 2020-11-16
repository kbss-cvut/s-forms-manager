package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenVersionTag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormGenVersionTagDAO extends BaseDao<FormGenVersionTag> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenVersionTagDAO.class);

    @Autowired
    protected FormGenVersionTagDAO(EntityManager em) {
        super(em, FormGenVersionTag.class);
    }
}
