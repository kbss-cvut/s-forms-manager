package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenInstance;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenVersion;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenSaveDBResponse;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormGenInstanceDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormGenMetadataDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormGenVersionDAO;
import cz.cvut.kbss.sformsmanager.service.model.remote.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
public class FormGenProcessingServiceImpl implements FormGenProcessingService {

    private final FormGenInstanceDAO instanceDAO;
    private final FormGenVersionDAO versionDAO;
    private final FormGenMetadataDAO metadataDAO;

    private final ContextService contextService;

    @Autowired
    public FormGenProcessingServiceImpl(FormGenInstanceDAO instanceDAO, FormGenVersionDAO versionTagDAO, FormGenMetadataDAO metadataDAO, ContextService contextService) {
        this.instanceDAO = instanceDAO;
        this.versionDAO = versionTagDAO;
        this.metadataDAO = metadataDAO;
        this.contextService = contextService;
    }

    @Transactional
    public FormGenMetadata processFormGen(String connectionName, String contextUri) throws IOException {

        // formGen
        String formGenMetadataKey = FormGenMetadata.createKey(connectionName, contextUri);
        Optional<FormGenMetadata> formGenMetadataOptional = metadataDAO.findByKey(formGenMetadataKey);
        if (formGenMetadataOptional.isPresent()) {
            return formGenMetadataOptional.get();
        }

        String versionHash = contextService.getFormGenVersionIdentifier(connectionName, contextUri);
        String versionKey = FormGenVersion.createKey(connectionName, versionHash);
        Optional<FormGenVersion> versionTagOptional = versionDAO.findByKey(versionKey);
        FormGenVersion formGenVersion = versionTagOptional.orElse(
                new FormGenVersion(connectionName, versionDAO.count(), versionHash, contextUri));

        // formGen instance
        String instanceHash = contextService.getFormGenInstanceHash(connectionName, contextUri);
        String instanceKey = FormGenInstance.createKey(connectionName, instanceHash);
        Optional<FormGenInstance> instanceOptional = instanceDAO.findByKey(instanceKey);
        FormGenInstance formGenInstance = instanceOptional.orElse(
                new FormGenInstance(connectionName, contextUri, instanceHash));

        // formGen instance
        FormGenSaveDBResponse formGenSave = contextService.getFormGenSaveHash(connectionName, contextUri);
        String saveHash = formGenSave.getFormGenSaveHash();
        Date formGenCreated = formGenSave.getCreated();
        Date formGenModified = formGenSave.getModified() != null ? formGenSave.getModified() : formGenSave.getCreated();

        FormGenMetadata formGenMetadata = new FormGenMetadata(formGenVersion, formGenInstance, saveHash, formGenCreated, formGenModified, contextUri, connectionName);
        return metadataDAO.update(formGenMetadata);
    }
}
