package cz.cvut.kbss.sformsmanager.service;

import cz.cvut.kbss.sformsmanager.model.RdfContext;
import cz.cvut.kbss.sformsmanager.persistence.dao.ContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContextService {

    private final ContextRepository contextRepository;

    @Transactional
    public List<RdfContext> findAll() {
        return contextRepository.findAll();
    }
}
