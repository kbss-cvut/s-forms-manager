package cz.cvut.kbss.sformsmanager.service;

import cz.cvut.kbss.sformsmanager.model.Context;
import cz.cvut.kbss.sformsmanager.persistence.dao.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContextService {

    private final FormGenMetadataService formGenMetadataService;
    private final ContextRepository contextRepository;

    @Autowired
    public ContextService(ContextRepository contextRepository, FormGenMetadataService formGenMetadataService1) {
        this.contextRepository = contextRepository;
        this.formGenMetadataService = formGenMetadataService1;
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
                .map(context -> new Context(context.getUriString(), processedContexts.contains(context)))
                .collect(Collectors.toList());
    }

    public List<Context> getPaginatedContexts(String connectionName, int offset, int limit) {
        Set<String> processedContexts = formGenMetadataService.findProcessedForms(connectionName);
        return contextRepository.findPaginated(connectionName, offset, limit).stream()
                .map(context -> new Context(context.getUriString(), processedContexts.contains(context.getUriString())))
                .collect(Collectors.toList());
    }
}
