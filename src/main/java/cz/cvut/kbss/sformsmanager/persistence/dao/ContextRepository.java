package cz.cvut.kbss.sformsmanager.persistence.dao;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.RdfContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ContextRepository {

    private final EntityManager em;

    private List<RdfContext> demoData = List.of(
            RdfContext.create("http://vfn.cz/ontologies/study-manager/formGen1543244479389"),
            RdfContext.create("http://vfn.cz/ontologies/study-manager/formGen1601802887303"),
            RdfContext.create("http://vfn.cz/ontologies/study-manager/formGen1601803072953"),
            RdfContext.create("http://vfn.cz/ontologies/study-manager/formGen1601838891390"),
            RdfContext.create("http://vfn.cz/ontologies/study-manager/formGen1601803455016"));

    public List<RdfContext> findAll() {
        // demo data + data in the repository

        List<RdfContext> list = em.getContexts().stream().map(RdfContext::new).collect(Collectors.toList());
        list.addAll(demoData);
        return list;
    }
}
