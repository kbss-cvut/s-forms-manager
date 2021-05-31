package cz.cvut.kbss.sformsmanager.service.formgen;

import cz.cvut.kbss.sformsmanager.model.dto.SFormsRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.local.Project;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.ProjectDAO;
import cz.cvut.kbss.sformsmanager.service.data.RemoteDataLoader;
import org.eclipse.rdf4j.repository.Repository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RemoteFormGenJsonLoader implements FormGenJsonLoader {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RemoteFormGenJsonLoader.class);

    private final RemoteDataLoader dataLoader;
    private final ProjectDAO projectDAO;
    private final Repository repository;

    private static final String REPOSITORY_URL_PARAM = "repositoryUrl";
    private static final String FORMGEN_REPOSITORY_URL_PARAM = "formGenRepositoryUrl";
    private static final String RECORD_GRAPH_ID_PARAM = "recordGraphId";

    @Autowired
    public RemoteFormGenJsonLoader(RemoteDataLoader dataLoader, ProjectDAO projectDAO, Repository repository) {
        this.dataLoader = dataLoader;
        this.projectDAO = projectDAO;
        this.repository = repository;
    }

    /**
     * Service for generating forms at a 'connected repository'.
     *
     * @param projectName
     * @param contextUri
     * @return
     * @throws URISyntaxException
     */
    public SFormsRawJson getFormGenRawJson(String projectName, URI contextUri) throws URISyntaxException {
        Project project = projectDAO.findByKey(projectName, projectName).orElseThrow(
                () -> new RuntimeException(String.format("Repository connection with project descriptor '%s' does not exist.", projectName)));

        final Map<String, String> params = new HashMap<>();
        params.put(RECORD_GRAPH_ID_PARAM, contextUri.toString());
        params.put(REPOSITORY_URL_PARAM, project.getAppRepositoryUrl());
        params.put(FORMGEN_REPOSITORY_URL_PARAM, project.getFormGenRepositoryUrl());

        String rawFormJson = dataLoader.loadDataFromUrl(project.getFormGenServiceUrl(), params, Collections.emptyMap());
        return new SFormsRawJson(projectName, contextUri.toString(), rawFormJson);
    }

    public String getFormGenPossibleValues(String query) throws URISyntaxException {
        return dataLoader.loadDataFromUrl(query, Collections.emptyMap(), Collections.emptyMap());
    }
}