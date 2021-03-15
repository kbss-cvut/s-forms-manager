package cz.cvut.kbss.sformsmanager.service.model.local;


import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenLatestAndNewestDateDBResponse;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenLatestSavesResponseDB;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenVersionHistogramDBResponse;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormGenMetadataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FormGenMetadataService {

    private final FormGenMetadataDAO formGenMetadataDAO;

    @Autowired
    public FormGenMetadataService(FormGenMetadataDAO formGenMetadataDAO) {
        this.formGenMetadataDAO = formGenMetadataDAO;
    }

    public Optional<FormGenMetadata> findByKey(String key) {
        return Optional.empty();
//        return formGenMetadataDAO.findByKey(key);
    }

    public Set<String> findProcessedForms(String connectionName) {
        return null;
//        return formGenMetadataDAO.findAllInConnection(connectionName).stream() // TODO: move to RecordSnapshotDAO, get only REMOTE URI
//                .map(metadata -> metadata.getContextUri())
//                .collect(Collectors.toSet());
    }

    public int getConnectionCount(String connectionName) {
        return 0;
//        return formGenMetadataDAO.countAllInConnection(connectionName);
    }

    public int getConnectionNonEmptyCount(String connectionName) {
        return formGenMetadataDAO.countAllNonEmptyInConnection(connectionName);
    }

    public int getConnectionCountByVersion(String connectionName, String versionKey) {
        return 0;
//        return formGenMetadataDAO.countAllInConnectionByVersion(connectionName, versionKey);
    }

    public List<FormGenMetadata> findAllInConnection(String connectionName) {
        return null;
//        return formGenMetadataDAO.findAllInConnection(connectionName);
    }

    public List<FormGenMetadata> findHistoryOfFormGen(String connectionName, String saveHash) {
        return formGenMetadataDAO.findAllWithSaveHash(connectionName, saveHash);
    }

    public List<FormGenLatestSavesResponseDB> getFormGensWithHistoryCount(String connectionName) throws IOException {
        return formGenMetadataDAO.getFormListingWithHistory(connectionName);
    }

    public FormGenLatestAndNewestDateDBResponse getHistogramBounds(String connectionName) throws IOException {
        return formGenMetadataDAO.getOldestAndLatestDate(connectionName);
    }

    public List<FormGenVersionHistogramDBResponse> getHistogramData(String connectionName) throws IOException {
        return formGenMetadataDAO.getVersionHistogramDataByConnectionName(connectionName);
    }

    public List<FormGenMetadata> runSearchQuery(String searchQuery) {
        return formGenMetadataDAO.runSearchQuery(searchQuery);
    }
}
