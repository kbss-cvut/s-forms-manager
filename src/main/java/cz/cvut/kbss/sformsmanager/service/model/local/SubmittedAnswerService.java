package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.SubmittedAnswer;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.RecordSnapshotDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.SubmittedAnswerDAO;
import cz.cvut.kbss.sformsmanager.rest.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SubmittedAnswerService {

    private final SubmittedAnswerDAO submittedAnswerDAO;
    private final RecordSnapshotDAO recordSnapshotService;

    @Autowired
    public SubmittedAnswerService(SubmittedAnswerDAO submittedAnswerDAO, RecordSnapshotDAO recordSnapshotService) {
        this.submittedAnswerDAO = submittedAnswerDAO;
        this.recordSnapshotService = recordSnapshotService;
    }

    public int count(String projectName) {
        return submittedAnswerDAO.count(projectName);
    }

    public Set<SubmittedAnswer> getRecordSnapshotAnswers(String projectName, String recordSnapshotContextUri) {
        return recordSnapshotService.findByKey(projectName, recordSnapshotContextUri).orElseThrow(() -> new NotFoundException("Record snapshot was not found: " + recordSnapshotContextUri)).getAnswers();
    }
}
