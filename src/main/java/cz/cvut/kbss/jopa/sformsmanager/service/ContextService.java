package cz.cvut.kbss.jopa.sformsmanager.service;

import cz.cvut.kbss.jopa.sformsmanager.persistence.dao.ContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContextService {

    private final ContextRepository contextRepository;

    @Transactional
    public List<URI> findAll() {
        return contextRepository.findAll();
    }


}
