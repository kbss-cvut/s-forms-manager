package cz.cvut.kbss.sformsmanager.persistence.dao.local.custom;

import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SearchDAO extends CustomQueryDAO {

    private static final String AUTOCOMPLETE_QUERY_FILE = CLASSPATH_PREFIX + "templates/search/questionAutocomplete.sparql";

    @Autowired
    protected SearchDAO(EntityManager em) {
        super(em);
    }

    public List<Object> runSearchQuery(String searchQuery) {
        return executeOnEntityManagerList(
                em -> em.createNativeQuery(searchQuery)
                        .getResultList(),
                "Could not run custom search query.");

    }

    public List<String> findAutocompleteValues(String projectName, int depth, String questionOriginPath) throws IOException {
        String autocompleteQuery = getQueryFromFile(AUTOCOMPLETE_QUERY_FILE);
        return executeOnEntityManagerList(
                em -> em.createNativeQuery(autocompleteQuery)
                        .setParameter(CUSTOM_QUERY_CONTEXT_URI_PARAM, getProjectContextURI(projectName))
                        .setParameter("questionOriginPath", questionOriginPath)
                        .setParameter("questionDepth", depth)
                        .getResultList(),
                "Could not run autocomplete search query.");

    }
}

