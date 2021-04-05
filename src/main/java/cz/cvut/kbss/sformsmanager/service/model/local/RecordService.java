package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.Record;
import cz.cvut.kbss.sformsmanager.model.persisted.local.RecordSnapshot;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.RecordDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.RecordSnapshotDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.RecordVersionDAO;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class RecordService {

    private final RecordDAO recordDAO;
    private final RecordSnapshotDAO recordSnapshotDAO;
    private final RecordVersionDAO recordVersionDAO;

    public RecordService(RecordDAO recordDAO, RecordSnapshotDAO recordSnapshotDAO, RecordVersionDAO recordVersionDAO) {
        this.recordDAO = recordDAO;
        this.recordSnapshotDAO = recordSnapshotDAO;
        this.recordVersionDAO = recordVersionDAO;
    }

    public int countRecords(String projectName) {
        return recordDAO.count(projectName);
    }

    public int countRecordVersions(String projectName) {
        return recordVersionDAO.count(projectName);
    }

    public int countRecordVersionsForRecord(String projectName, Record record) {
        return recordVersionDAO.countWhere(projectName, Vocabulary.p_hasRecord, record.getUri());
    }

    public int countRecordSnapshotsForRecord(String projectName, Record record) {
        return recordSnapshotDAO.countWhere(projectName, Vocabulary.p_hasRecord, record.getUri());
    }

    public int countRecordVersionAnswersForRecord(String projectName, Record record) {
        return recordVersionDAO.countWhere(projectName, Vocabulary.p_hasRecord, record.getUri());
    }

    public int countRecordSnapshots(String projectName) {
        return recordSnapshotDAO.count(projectName);
    }

    public List<Record> findAllNonEmptyRecords(String projectName) {
        return recordDAO.findAllWithRecordVersion(projectName);
    }

    public int countAllNonEmptyRecordSnapshots(String projectName) {
        return recordSnapshotDAO.countAllWithFormTemplateVersion(projectName);
    }

    public List<RecordSnapshot> findRecordSnapshotsForRecord(String projectName, String recordURI) {
        return recordSnapshotDAO.findAllWhere(projectName, Vocabulary.p_hasRecord, URI.create(recordURI));
    }

}
