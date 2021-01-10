package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenVersion;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormGenVersionDAO extends LocalWithConnectionBaseDAO<FormGenVersion> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenVersionDAO.class);

    @Autowired
    protected FormGenVersionDAO(EntityManager em) {
        super(em, FormGenVersion.class);
    }
}
