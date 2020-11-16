package cz.cvut.kbss.sformsmanager.service;

import cz.cvut.kbss.sformsmanager.model.RdfContext;
import cz.cvut.kbss.sformsmanager.persistence.dao.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContextService {

    @Autowired
    public ContextService(
            ContextRepository contextRepository) {
        this.contextRepository = contextRepository;
    }

    private final ContextRepository contextRepository;

    @Transactional
    public List<RdfContext> findAll(String repositoryName) {
        return contextRepository.findAll(repositoryName);
    }
}
