package cz.cvut.kbss.sformsmanager.service;


import cz.cvut.kbss.sformsmanager.model.persisted.FormGenInstance;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenInstanceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormGenInstanceService {

    private final FormGenInstanceDAO instanceDAO;

    @Autowired
    public FormGenInstanceService(FormGenInstanceDAO instanceDAO) {
        this.instanceDAO = instanceDAO;
    }

    public Optional<FormGenInstance> findByKey(String key) {
        return instanceDAO.findByKey(key);
    }

    public int getConnectionCount(String connectionName) {
        return instanceDAO.count(connectionName);
    }
}
