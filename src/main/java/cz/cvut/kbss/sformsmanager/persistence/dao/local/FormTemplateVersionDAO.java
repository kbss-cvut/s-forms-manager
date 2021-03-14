package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.FormTemplateVersion;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormTemplateVersionDAO extends LocalWithConnectionBaseDAO<FormTemplateVersion> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormTemplateVersionDAO.class);

    @Autowired
    protected FormTemplateVersionDAO(EntityManager em) {
        super(em, FormTemplateVersion.class);
    }
}
