package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FormGenMetadataDAO extends BaseDao<FormGenMetadata> {

    @Autowired
    protected FormGenMetadataDAO(EntityManager em) {
        super(em, FormGenMetadata.class);
    }
}
