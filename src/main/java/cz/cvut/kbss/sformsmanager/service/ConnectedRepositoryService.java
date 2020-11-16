package cz.cvut.kbss.sformsmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.cvut.kbss.sformsmanager.config.provider.ConnectedRepositoryConfigProvider;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenMetadataDAO;
import cz.cvut.kbss.sformsmanager.service.data.RemoteDataLoader;
import cz.cvut.kbss.sformsmanager.service.process.FormGenProcessingService;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConnectedRepositoryService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ConnectedRepositoryService.class);
    
    private final RemoteDataLoader dataLoader;
    private final ConnectedRepositoryConfigProvider connectedRepositoryConfigProvider;
    private final FormGenMetadataDAO formGenMetadataDAO;
    private final FormGenProcessingService formGenProcessingService;

    private static final String REPOSITORY_URL_PARAM = "repositoryUrl";
    private static final String FORMGEN_REPOSITORY_URL_PARAM = "formGenRepositoryUrl";
    private static final String RECORD_GRAPH_ID_PARAM = "recordGraphId";

    @Autowired
    public ConnectedRepositoryService(
            RemoteDataLoader dataLoader,
            ConnectedRepositoryConfigProvider connectedRepositoryConfigProvider,
            FormGenMetadataDAO formGenMetadataDAO,
            FormGenProcessingService formGenProcessingService) {
        this.dataLoader = dataLoader;
        this.connectedRepositoryConfigProvider = connectedRepositoryConfigProvider;
        this.formGenMetadataDAO = formGenMetadataDAO;
        this.formGenProcessingService = formGenProcessingService;
    }

    /**
     * Cached service for generating forms at a 'connected repository'.
     *
     * @param connectionName
     * @param contextUri
     * @return
     * @throws URISyntaxException
     */
    public FormGenRawJson getFormGenRawJsonFromConnection(String connectionName, String contextUri) throws URISyntaxException {
        ConnectedRepositoryConfigProvider.ConnectedRepositoryConfig connection = connectedRepositoryConfigProvider.getConnectionMap().get(connectionName);

        final Map<String, String> params = new HashMap<>();
        params.put(RECORD_GRAPH_ID_PARAM, contextUri);
        params.put(REPOSITORY_URL_PARAM, connection.getAppRepositoryUrl());
        params.put(FORMGEN_REPOSITORY_URL_PARAM, connection.getFormGenRepositoryUrl());

        String rawFormJson = dataLoader.loadData(connection.getFormGenServiceUrl(), params, Collections.emptyMap());
        String formGenKey = OWLUtils.createFormGenkey(connectionName, contextUri);

        return FormGenRawJson.builder()
                .connectionName(connectionName)
                .key(formGenKey)
                .contextUri(contextUri)
                .rawJson(rawFormJson)
                .build();
    }

    @Transactional
    public FormGenRawJson getFormGenRawJsonFromConnectionAndSaveMetadata(String connectionName, String contextUri) throws URISyntaxException, JsonProcessingException {
        FormGenRawJson formGenRawJson = getFormGenRawJsonFromConnection(connectionName, contextUri);
        FormGenMetadata formGenMetadata = formGenProcessingService.getFormGenMetadata(formGenRawJson);
        formGenMetadataDAO.update(formGenMetadata);

        return formGenRawJson;
    }
}

