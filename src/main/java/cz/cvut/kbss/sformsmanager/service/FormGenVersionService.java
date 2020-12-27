package cz.cvut.kbss.sformsmanager.service;


import cz.cvut.kbss.sformsmanager.model.persisted.FormGenVersion;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenVersionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormGenVersionService {

    private final FormGenVersionDAO versionDAO;

    @Autowired
    public FormGenVersionService(FormGenVersionDAO versionDAO) {
        this.versionDAO = versionDAO;
    }

    public Optional<FormGenVersion> findByKey(String key) {
        return versionDAO.findByKey(key);
    }

    public int getConnectionCount(String connectionName) {
        return versionDAO.count(connectionName);
    }
}
