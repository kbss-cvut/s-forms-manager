package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.persistence.dao.local.RecordDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.RecordSnapshotDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.RecordVersionDAO;
import org.springframework.stereotype.Service;

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

    public int countRecordSnapshots(String projectName) {
        return recordSnapshotDAO.count(projectName);
    }
}
