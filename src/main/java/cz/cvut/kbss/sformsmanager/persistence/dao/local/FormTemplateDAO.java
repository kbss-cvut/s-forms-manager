package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.FormTemplate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormTemplateDAO extends LocalWithConnectionBaseDAO<FormTemplate> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormTemplateDAO.class);

    @Autowired
    protected FormTemplateDAO(EntityManager em) {
        super(em, FormTemplate.class);
    }
}
