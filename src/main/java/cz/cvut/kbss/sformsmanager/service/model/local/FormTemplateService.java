package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormTemplateVersion;
import cz.cvut.kbss.sformsmanager.model.persisted.response.VersionHistogramQueryResult;
import cz.cvut.kbss.sformsmanager.model.persisted.response.VersionHistoryBoundsQueryResult;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormTemplateDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormTemplateVersionDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.RecordSnapshotDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.custom.HistogramDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class FormTemplateService {

    private final FormTemplateVersionDAO formTemplateVersionDAO;
    private final FormTemplateDAO formTemplateDAO;
    private final HistogramDAO histogramDAO;
    private final RecordSnapshotDAO recordSnapshotDAO;

    @Autowired
    public FormTemplateService(FormTemplateVersionDAO formTemplateVersionDAO, FormTemplateDAO formTemplateDAO, HistogramDAO histogramDAO, RecordSnapshotDAO recordSnapshotDAO) {
        this.formTemplateVersionDAO = formTemplateVersionDAO;
        this.formTemplateDAO = formTemplateDAO;
        this.histogramDAO = histogramDAO;
        this.recordSnapshotDAO = recordSnapshotDAO;
    }

    public int countFormTemplates(String projectName) {
        return formTemplateDAO.count(projectName);
    }

    public int countFormTemplateVersions(String projectName) {
        return formTemplateVersionDAO.count(projectName);
    }

    public List<FormTemplateVersion> findAllVersions(String projectName) {
        return formTemplateVersionDAO.findAll(projectName);
    }

    @Transactional
    public FormTemplateVersion updateVersion(String projectName, FormTemplateVersion formTemplateVersion) {
        return formTemplateVersionDAO.update(projectName, formTemplateVersion);
    }

    public Optional<FormTemplateVersion> findVersionByKey(String projectName, String formTemplateVersionKey) {
        return formTemplateVersionDAO.findByKey(projectName, formTemplateVersionKey);
    }

    public int countFormTemplateVersionRecordSnapshots(String projectName, URI formTemplateVersionURI) {
        return recordSnapshotDAO.countWhere(projectName, Vocabulary.p_hasFormTemplateVersion, formTemplateVersionURI);
    }

    public VersionHistoryBoundsQueryResult getHistogramBounds(String projectName) throws IOException {
        return histogramDAO.getOldestAndLatestDate(projectName);
    }

    public List<VersionHistogramQueryResult> getHistogramData(String projectName) throws IOException {
        return histogramDAO.getVersionHistogramData(projectName);
    }
}
