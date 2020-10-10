package cz.cvut.kbss.sformsmanager.service;

import cz.cvut.kbss.sformsmanager.config.provider.ConnectionProvider;
import cz.cvut.kbss.sformsmanager.model.JsonLDForm;
import cz.cvut.kbss.sformsmanager.service.data.RemoteDataLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataRepositoryService {

    private final RemoteDataLoader dataLoader;
    private final ConnectionProvider connectionProvider;

    private static final String REPOSITORY_URL_PARAM = "repositoryUrl";
    private static final String FORMGEN_REPOSITORY_URL_PARAM = "formGenRepositoryUrl";
    private static final String RECORD_GRAPH_ID_PARAM = "recordGraphId";

    /**
     * Cached service for generating forms at a 'connected repository'.
     *
     * @param connectionName
     * @param contextUri
     * @return
     * @throws URISyntaxException
     */
    @Cacheable(value = "generatedForms", cacheManager = "formsCacheManager")
    public JsonLDForm getFormFromConnection(String connectionName, String contextUri) throws URISyntaxException {
        ConnectionProvider.RepositoryConnection connection = connectionProvider.getConnectionMap().get(connectionName);

        final Map<String, String> params = new HashMap<>();
        params.put(RECORD_GRAPH_ID_PARAM, contextUri);
        params.put(REPOSITORY_URL_PARAM, connection.getAppRepositoryUrl());
        params.put(FORMGEN_REPOSITORY_URL_PARAM, connection.getFormGenRepositoryUrl());

        String rawFormJson = dataLoader.loadData(connection.getFormGenServiceUrl(), params, Collections.emptyMap());
        String formKey = connectionName.concat(contextUri);

        return JsonLDForm.builder().key(formKey).rawJson(rawFormJson).build();
    }

}

