package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormGenMetadataDAO extends BaseDao<FormGenMetadata> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenMetadataDAO.class);

    @Autowired
    protected FormGenMetadataDAO(EntityManager em) {
        super(em, FormGenMetadata.class);
    }
}
