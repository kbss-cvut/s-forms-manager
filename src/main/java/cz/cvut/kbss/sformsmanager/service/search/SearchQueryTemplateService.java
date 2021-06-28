package cz.cvut.kbss.sformsmanager.service.search;

import cz.cvut.kbss.sformsmanager.exception.ResourceNotFoundException;
import cz.cvut.kbss.sformsmanager.persistence.dao.LocalEntityBaseDAO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class SearchQueryTemplateService {

    public static final String QUERY_DIRECTORY_PREFIX = "search/";

    private static final Map<String, String> QUERY_IDS_AND_FILES = new HashMap<String, String>() {{
        put("usecase1", QUERY_DIRECTORY_PREFIX + "usecase1.sparql"); // value must be lowercase
        put("usecase2", QUERY_DIRECTORY_PREFIX + "usecase2.sparql");
        put("usecase3", QUERY_DIRECTORY_PREFIX + "usecase3.sparql");
        put("usecase4", QUERY_DIRECTORY_PREFIX + "usecase4.sparql");
    }};

    private freemarker.template.Configuration templateCfg;

    @Autowired
    public SearchQueryTemplateService(freemarker.template.Configuration templateCfg) {
        this.templateCfg = templateCfg;
    }

    public String getQuery(String projectName, String queryId, String parameter1, String parameter2) throws IOException, TemplateException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("contextUri", LocalEntityBaseDAO.getProjectContextURI(projectName).toString());
        if (parameter1 != null) parameters.put("PARAMETER_1", parameter1);
        if (parameter2 != null) parameters.put("PARAMETER_2", parameter2);

        String fileName = QUERY_IDS_AND_FILES.get(queryId.toLowerCase());
        if (fileName == null) {
            throw new ResourceNotFoundException("Use case query not found: " + queryId);
        }

        Template temp = templateCfg.getTemplate(fileName);
        StringWriter stringWriter = new StringWriter();
        temp.process(parameters, stringWriter);

        return stringWriter.toString();
    }
}
