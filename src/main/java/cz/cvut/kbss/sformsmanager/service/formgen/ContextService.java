package cz.cvut.kbss.sformsmanager.service.formgen;

import cz.cvut.kbss.sformsmanager.model.persisted.remote.Context;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.RecordSnapshotDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.remote.ContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContextService {

    private final ContextRepository contextRepository;
    private final RecordSnapshotDAO recordSnapshotDAO;

    public ContextService(ContextRepository contextRepository, RecordSnapshotDAO recordSnapshotDAO) {
        this.contextRepository = contextRepository;
        this.recordSnapshotDAO = recordSnapshotDAO;
    }

    @Transactional
    public int count(String projectName) {
        return contextRepository.count(projectName);
    }

    public List<Context> getContexts(String projectName) {
        Set<String> processedContexts = new HashSet<>(recordSnapshotDAO.findSnapshotRemoteURIs(projectName));
        return contextRepository.findAll(projectName).stream()
                .map(context -> new Context(context.getUriString(), processedContexts.contains(context.getUriString())))
                .collect(Collectors.toList());
    }

    public List<Context> getPaginatedContexts(String projectName, int offset, int limit) {
        Set<String> processedContexts = new HashSet<>(recordSnapshotDAO.findSnapshotRemoteURIs(projectName));
        return contextRepository.findPaginated(projectName, offset, limit).stream()
                .map(context -> new Context(context.getUriString(), processedContexts.contains(context.getUriString())))
                .collect(Collectors.toList());
    }
}
