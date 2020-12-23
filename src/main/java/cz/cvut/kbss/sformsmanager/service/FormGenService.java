package cz.cvut.kbss.sformsmanager.service;


import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenMetadataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FormGenService {

    private final FormGenMetadataDAO formGenMetadataDAO;

    @Autowired
    public FormGenService(FormGenMetadataDAO formGenMetadataDAO) {
        this.formGenMetadataDAO = formGenMetadataDAO;
    }

    public Optional<FormGenMetadata> findByKey(String key) {
        return formGenMetadataDAO.findByKey(key);
    }

    public Set<String> findProcessedContexts(String connectionName) {
        return formGenMetadataDAO.findByConnectionName(connectionName).stream()
                .map(metadata -> metadata.getContextUri())
                .collect(Collectors.toSet());
    }

    public int getConnectionCount(String connectionName) {
        return formGenMetadataDAO.count(connectionName);
    }
}
