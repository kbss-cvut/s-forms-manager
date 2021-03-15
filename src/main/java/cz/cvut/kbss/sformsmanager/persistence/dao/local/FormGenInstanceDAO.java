package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenInstance;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormGenInstanceDAO extends LocalEntityBaseDAO<FormGenInstance> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenInstanceDAO.class);

    @Autowired
    protected FormGenInstanceDAO(EntityManager em) {
        super(em, FormGenInstance.class);
    }

}
