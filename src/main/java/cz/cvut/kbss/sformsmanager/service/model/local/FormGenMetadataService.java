package cz.cvut.kbss.sformsmanager.service.model.local;


import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormGenMetadataDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.response.StringIntDateStringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FormGenMetadataService {

    private final FormGenMetadataDAO formGenMetadataDAO;

    @Autowired
    public FormGenMetadataService(FormGenMetadataDAO formGenMetadataDAO) {
        this.formGenMetadataDAO = formGenMetadataDAO;
    }

    public Optional<FormGenMetadata> findByKey(String key) {
        return formGenMetadataDAO.findByKey(key);
    }

    public Set<String> findProcessedForms(String connectionName) {
        return formGenMetadataDAO.findAllInConnection(connectionName).stream()
                .map(metadata -> metadata.getContextUri())
                .collect(Collectors.toSet());
    }

    public int getConnectionCount(String connectionName) {
        return formGenMetadataDAO.countAllInConnection(connectionName);
    }

    public int getConnectionNonEmptyCount(String connectionName) {
        return formGenMetadataDAO.countAllNonEmptyInConnection(connectionName);
    }

    public int getConnectionCountByVersion(String connectionName, String versionKey) {
        return formGenMetadataDAO.countAllInConnectionByVersion(connectionName, versionKey);
    }

    public List<FormGenMetadata> findAllInConnection(String connectionName) {
        return formGenMetadataDAO.findAllInConnection(connectionName);
    }

    public List<StringIntDateStringResponse> getLatestFormGenSaves(String connectionName) {
        return formGenMetadataDAO.getLatestFormGensWithHistoryCount(connectionName);
    }

}
