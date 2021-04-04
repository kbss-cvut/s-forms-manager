package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.persistence.dao.local.custom.SearchDAO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SearchService {

    private final SearchDAO searchDAO;

    public SearchService(SearchDAO searchDAO) {
        this.searchDAO = searchDAO;
    }

    public List<Object> runSearchQuery(String searchQuery) {
        return searchDAO.runSearchQuery(searchQuery);
    }

    public List<String> getAutocompleteValues(String projectName, Integer depth, String questionOriginPath) throws IOException {
        return searchDAO.findAutocompleteValues(projectName, depth, questionOriginPath);
    }
}
