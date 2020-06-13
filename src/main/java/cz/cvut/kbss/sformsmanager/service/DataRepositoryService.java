package cz.cvut.kbss.sformsmanager.service;

import cz.cvut.kbss.sformsmanager.config.provider.ConnectionProvider;
import cz.cvut.kbss.sformsmanager.service.data.RemoteDataLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
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

    public String getFormFromConnection(String connectionName, URI contextUri) throws URISyntaxException {
        ConnectionProvider.RepositoryConnection connection = connectionProvider.getConnectionMap().get(connectionName);

        final Map<String, String> params = new HashMap<>();
        params.put(RECORD_GRAPH_ID_PARAM, contextUri.toString());
        params.put(REPOSITORY_URL_PARAM, connection.getAppRepositoryUrl());
        params.put(FORMGEN_REPOSITORY_URL_PARAM, connection.getFormGenRepositoryUrl());
        return dataLoader.loadData(connection.getFormGenServiceUrl(), params);
    }
}

