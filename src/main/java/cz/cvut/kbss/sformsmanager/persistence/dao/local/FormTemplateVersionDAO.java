package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormTemplateVersion;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class FormTemplateVersionDAO extends LocalEntityBaseDAO<FormTemplateVersion> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormTemplateVersionDAO.class);

    protected FormTemplateVersionDAO(EntityManager em) {
        super(em, FormTemplateVersion.class);
    }
}
