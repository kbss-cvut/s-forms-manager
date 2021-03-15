package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.Record;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordDAO extends LocalEntityBaseDAO<Record> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RecordDAO.class);

    @Autowired
    protected RecordDAO(EntityManager em) {
        super(em, Record.class);
    }
}
