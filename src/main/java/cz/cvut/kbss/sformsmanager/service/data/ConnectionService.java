package cz.cvut.kbss.sformsmanager.service.data;

import cz.cvut.kbss.sformsmanager.model.persisted.Connection;
import cz.cvut.kbss.sformsmanager.persistence.dao.ConnectionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConnectionService {

    private final ConnectionDAO connectionDAO;

    @Autowired
    public ConnectionService(ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    public List<Connection> findAll() {
        return connectionDAO.findAll();
    }

    public void create(Connection connection) {
        connectionDAO.persist(connection);
    }

    public Optional<Connection> findByKey(String connectionName) {
        return connectionDAO.findByKey(connectionName);
    }

    public void update(Connection connection) {
        connectionDAO.update(connection);
    }

    public void delete(Connection connection) {
        connectionDAO.remove(connection);
    }

}
