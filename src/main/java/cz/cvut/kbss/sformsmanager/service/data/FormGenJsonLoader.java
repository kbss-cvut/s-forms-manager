package cz.cvut.kbss.sformsmanager.service.data;

import cz.cvut.kbss.sformsmanager.model.dto.FormGenRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.local.Project;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.ProjectDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FormGenJsonLoader {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenJsonLoader.class);

    private final RemoteDataLoader dataLoader;
    private final ProjectDAO projectDAO;

    private static final String REPOSITORY_URL_PARAM = "repositoryUrl";
    private static final String FORMGEN_REPOSITORY_URL_PARAM = "formGenRepositoryUrl";
    private static final String RECORD_GRAPH_ID_PARAM = "recordGraphId";

    @Autowired
    public FormGenJsonLoader(RemoteDataLoader dataLoader, ProjectDAO projectDAO) {
        this.dataLoader = dataLoader;
        this.projectDAO = projectDAO;
    }

    /**
     * Cached service for generating forms at a 'connected repository'.
     *
     * @param projectName
     * @param contextUri
     * @return
     * @throws URISyntaxException
     */
    public FormGenRawJson getFormGenRawJson(String projectName, String contextUri) throws URISyntaxException {
        Project project = projectDAO.findByKey(projectName, projectName).orElseThrow(
                () -> new RuntimeException(String.format("Repository connection with project descriptor '%s' does not exist.", projectName)));

        final Map<String, String> params = new HashMap<>();
        params.put(RECORD_GRAPH_ID_PARAM, contextUri);
        params.put(REPOSITORY_URL_PARAM, project.getAppRepositoryUrl());
        params.put(FORMGEN_REPOSITORY_URL_PARAM, project.getFormGenRepositoryUrl());

        String rawFormJson = dataLoader.loadData(project.getFormGenServiceUrl(), params, Collections.emptyMap());
        return new FormGenRawJson(projectName, contextUri, rawFormJson);
    }
}