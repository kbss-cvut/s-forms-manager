package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.RecordSnapshot;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordSnapshotDAO extends LocalWithConnectionBaseDAO<RecordSnapshot> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RecordSnapshotDAO.class);

    @Autowired
    protected RecordSnapshotDAO(EntityManager em) {
        super(em, RecordSnapshot.class);
    }
}
