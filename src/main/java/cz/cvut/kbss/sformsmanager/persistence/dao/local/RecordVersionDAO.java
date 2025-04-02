package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.RecordVersion;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class RecordVersionDAO extends LocalEntityBaseDAO<RecordVersion> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RecordVersionDAO.class);

    protected RecordVersionDAO(EntityManager em) {
        super(em, RecordVersion.class);
    }
}
