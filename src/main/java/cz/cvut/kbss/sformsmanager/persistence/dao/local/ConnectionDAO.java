package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.local.Connection;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionDAO extends LocalEntityBaseDAO<Connection> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ConnectionDAO.class);

    @Autowired
    protected ConnectionDAO(EntityManager em) {
        super(em, Connection.class);
    }
}
