package cz.cvut.kbss.sformsmanager.persistence.dao.local.custom;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenLatestAndNewestDateDBResponse;
import cz.cvut.kbss.sformsmanager.model.persisted.response.FormTemplateVersionHistogramQueryResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static cz.cvut.kbss.sformsmanager.model.persisted.response.FormGenLatestAndNewestDateDBResponse.LATEST_NEWEST_MAPPING;
import static cz.cvut.kbss.sformsmanager.model.persisted.response.FormTemplateVersionHistogramQueryResult.HISTOGRAM_MAPPING;

@Component
public class HistogramDAO extends CustomQueryDAO {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(HistogramDAO.class);

    private final String FORM_TEMPLATE_OLDEST_LATEST_DATE_FILE = CLASSPATH_PREFIX + "templates/local/newestAndOldestFormGen.sparql";
    private final String FORM_TEMPLATE_VERSION_HISTOGRAM_FILE = CLASSPATH_PREFIX + "templates/local/versionHistogram.sparql";

    @Autowired
    protected HistogramDAO(EntityManager em) {
        super(em);
    }

    public FormGenLatestAndNewestDateDBResponse getOldestAndLatestDate(String projectName) throws IOException {
        String query = getQueryFromFile(FORM_TEMPLATE_OLDEST_LATEST_DATE_FILE);
        return (FormGenLatestAndNewestDateDBResponse) executeOnEntityManagerSingleResult(em ->
                        em.createNativeQuery(query, LATEST_NEWEST_MAPPING)
                                .setParameter(CUSTOM_QUERY_CONTEXT_URI_PARAM, getProjectContextURI(projectName))
                                .getSingleResult(),
                "Could not find oldest and latest date for version histogram.");
    }

    public List<FormTemplateVersionHistogramQueryResult> getVersionHistogramDataByConnectionName(String projectName) throws IOException {
        String query = getQueryFromFile(FORM_TEMPLATE_VERSION_HISTOGRAM_FILE);
        return executeOnEntityManagerList(em ->
                        em.createNativeQuery(query, HISTOGRAM_MAPPING)
                                .setParameter(CUSTOM_QUERY_CONTEXT_URI_PARAM, getProjectContextURI(projectName))
                                .getResultList(),
                "Could not find histogram data.");
    }
}
