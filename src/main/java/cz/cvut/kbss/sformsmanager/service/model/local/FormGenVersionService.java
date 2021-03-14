package cz.cvut.kbss.sformsmanager.service.model.local;


import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenVersion;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormGenVersionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public Optional<FormGenVersion> findByVersionName(String versionName) {
        return versionDAO.findByVersionName(versionName);
    }

    public Optional<FormGenVersion> findByVersionNameOrSynonym(String versionOrSynonym) {
        return versionDAO.findByVersionNameOrSynonym(versionOrSynonym);
    }

    public int getConnectionCount(String connectionName) {
        return versionDAO.countAllInConnection(connectionName);
    }

    public List<FormGenVersion> findAllInConnection(String connectionName) {
        return versionDAO.findAllInConnection(connectionName);
    }

    @Transactional
    public void update(FormGenVersion version) {
        versionDAO.update(version);
    }
}
