package cz.cvut.kbss.sformsmanager.service.model.remote;

import cz.cvut.kbss.sformsmanager.model.persisted.remote.Context;
import cz.cvut.kbss.sformsmanager.persistence.dao.remote.ContextRepository;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenSaveDBResponse;
import cz.cvut.kbss.sformsmanager.persistence.dao.remote.RemoteFormGenDAO;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContextService {

    private final ContextRepository contextRepository;
    private final RemoteFormGenDAO remoteFormGenDAO;
    private final FormGenMetadataService formGenMetadataService;

    @Autowired
    public ContextService(ContextRepository contextRepository, RemoteFormGenDAO remoteFormGenDAO, FormGenMetadataService formGenMetadataService) {
        this.contextRepository = contextRepository;
        this.remoteFormGenDAO = remoteFormGenDAO;
        this.formGenMetadataService = formGenMetadataService;
    }

    @Transactional
    public int count(String connectionName) {
        return contextRepository.count(connectionName);
    }

    public List<Context> getContextsWithoutProcessed(String connectionName) {
        return contextRepository.findAll(connectionName);
    }

    public List<Context> getContexts(String connectionName) {
        Set<String> processedContexts = formGenMetadataService.findProcessedForms(connectionName);
        return contextRepository.findAll(connectionName).stream()
                .map(context -> new Context(context.getUriString(), processedContexts.contains(context.getUriString())))
                .collect(Collectors.toList());
    }

    public List<Context> getPaginatedContexts(String connectionName, int offset, int limit) {
        Set<String> processedContexts = formGenMetadataService.findProcessedForms(connectionName);
        return contextRepository.findPaginated(connectionName, offset, limit).stream()
                .map(context -> new Context(context.getUriString(), processedContexts.contains(context.getUriString())))
                .collect(Collectors.toList());
    }

    public String getFormGenVersionIdentifier(String connectionName, String contextUri) throws IOException {
        return remoteFormGenDAO.getFormGenVersionIdentifier(connectionName, contextUri);
    }

    public String getFormGenInstanceHash(String connectionName, String contextUri) throws IOException {
        return remoteFormGenDAO.getFormGenInstanceIdentifier(connectionName, contextUri);
    }

    public FormGenSaveDBResponse getFormGenSaveHash(String connectionName, String contextUri) throws IOException {
        return remoteFormGenDAO.getFormGenSaveIdentifier(connectionName, contextUri);
    }
}
