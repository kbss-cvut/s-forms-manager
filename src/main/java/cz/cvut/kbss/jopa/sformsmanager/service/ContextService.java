package cz.cvut.kbss.jopa.sformsmanager.service;

import cz.cvut.kbss.jopa.sformsmanager.persistence.dao.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
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
    public List<URI> findAll() {
        return contextRepository.findAll();
    }


}
