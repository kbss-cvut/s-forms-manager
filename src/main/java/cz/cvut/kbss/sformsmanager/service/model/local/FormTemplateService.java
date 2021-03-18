package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.FormTemplateVersion;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormTemplateDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormTemplateVersionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FormTemplateService {

    private final FormTemplateVersionDAO formTemplateVersionDAO;
    private final FormTemplateDAO formTemplateDAO;

    @Autowired
    public FormTemplateService(FormTemplateVersionDAO formTemplateVersionDAO, FormTemplateDAO formTemplateDAO) {
        this.formTemplateVersionDAO = formTemplateVersionDAO;
        this.formTemplateDAO = formTemplateDAO;
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
}
