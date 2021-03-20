package cz.cvut.kbss.sformsmanager.persistence.dao.local.custom;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.model.persisted.response.VersionHistogramQueryResult;
import cz.cvut.kbss.sformsmanager.model.persisted.response.VersionHistoryBoundsQueryResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static cz.cvut.kbss.sformsmanager.model.persisted.response.VersionHistogramQueryResult.HISTOGRAM_MAPPING;
import static cz.cvut.kbss.sformsmanager.model.persisted.response.VersionHistoryBoundsQueryResult.HISTORY_BOUNDS_MAPPING;

@Component
public class HistogramDAO extends CustomQueryDAO {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(HistogramDAO.class);

    private final String FORM_TEMPLATE_OLDEST_LATEST_DATE_FILE = CLASSPATH_PREFIX + "templates/local/versionHistoryBounds.sparql";
    private final String FORM_TEMPLATE_VERSION_HISTOGRAM_FILE = CLASSPATH_PREFIX + "templates/local/versionHistogram.sparql";

    @Autowired
    protected HistogramDAO(EntityManager em) {
        super(em);
    }

    public VersionHistoryBoundsQueryResult getOldestAndLatestDate(String projectName) throws IOException {
        String query = getQueryFromFile(FORM_TEMPLATE_OLDEST_LATEST_DATE_FILE);
        return (VersionHistoryBoundsQueryResult) executeOnEntityManagerSingleResult(em ->
                        em.createNativeQuery(query, HISTORY_BOUNDS_MAPPING)
                                .setParameter(CUSTOM_QUERY_CONTEXT_URI_PARAM, getProjectContextURI(projectName))
                                .getSingleResult(),
                "Could not find oldest and latest date for version histogram.");
    }

    public List<VersionHistogramQueryResult> getVersionHistogramData(String projectName) throws IOException {
        String query = getQueryFromFile(FORM_TEMPLATE_VERSION_HISTOGRAM_FILE);
        return executeOnEntityManagerList(em ->
                        em.createNativeQuery(query, HISTOGRAM_MAPPING)
                                .setParameter(CUSTOM_QUERY_CONTEXT_URI_PARAM, getProjectContextURI(projectName))
                                .getResultList(),
                "Could not find histogram data.");
    }
}
