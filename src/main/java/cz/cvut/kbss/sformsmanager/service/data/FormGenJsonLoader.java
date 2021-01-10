package cz.cvut.kbss.sformsmanager.service.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.local.Connection;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.ConnectionDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.FormGenMetadataDAO;
import cz.cvut.kbss.sformsmanager.service.process.FormGenProcessingService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FormGenJsonLoader {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenJsonLoader.class);

    private final RemoteDataLoader dataLoader;
    private final ConnectionDAO connectionDAO;
    private final FormGenMetadataDAO formGenMetadataDAO;
    private final FormGenProcessingService formGenProcessingService;

    private static final String REPOSITORY_URL_PARAM = "repositoryUrl";
    private static final String FORMGEN_REPOSITORY_URL_PARAM = "formGenRepositoryUrl";
    private static final String RECORD_GRAPH_ID_PARAM = "recordGraphId";

    @Autowired
    public FormGenJsonLoader(RemoteDataLoader dataLoader, ConnectionDAO connectionDAO, FormGenMetadataDAO formGenMetadataDAO, FormGenProcessingService formGenProcessingService) {
        this.dataLoader = dataLoader;
        this.connectionDAO = connectionDAO;
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
        Connection connection = connectionDAO.findByKey(connectionName).orElseThrow(
                () -> new RuntimeException(String.format("Repository connection with connectionName '%s' does not exist.", connectionName)));

        final Map<String, String> params = new HashMap<>();
        params.put(RECORD_GRAPH_ID_PARAM, contextUri);
        params.put(REPOSITORY_URL_PARAM, connection.getAppRepositoryUrl());
        params.put(FORMGEN_REPOSITORY_URL_PARAM, connection.getFormGenRepositoryUrl());

        String rawFormJson = dataLoader.loadData(connection.getFormGenServiceUrl(), params, Collections.emptyMap());
        return new FormGenRawJson(connectionName, contextUri, rawFormJson);
    }

    @Transactional
    public FormGenRawJson getFormGenRawJsonAndSaveMetadata(String connectionName, String contextUri) throws URISyntaxException, JsonProcessingException {
        FormGenRawJson formGenRawJson = getFormGenRawJsonFromConnection(connectionName, contextUri);
        FormGenMetadata formGenMetadata = formGenProcessingService.getFormGenMetadata(formGenRawJson);
        formGenMetadataDAO.update(formGenMetadata);

        return formGenRawJson;
    }
}