/**
 * Copyright (C) 2019 Czech Technical University in Prague
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.sformsmanager.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.cvut.kbss.sformsmanager.model.dto.ContextsStatsDTO;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenMetadataDTO;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenStatsDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.remote.Context;
import cz.cvut.kbss.sformsmanager.service.data.FormGenJsonLoader;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenInstanceService;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenMetadataService;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenVersionService;
import cz.cvut.kbss.sformsmanager.service.model.remote.ContextService;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;
import cz.cvut.kbss.sformsmanager.utils.PredicateUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Optional;

@RestController
@RequestMapping("/formGen")
public class FormGenController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenController.class);

    private final cz.cvut.kbss.sformsmanager.service.data.FormGenJsonLoader FormGenJsonLoader;
    private final FormGenMetadataService metadataService;
    private final FormGenInstanceService instanceService;
    private final FormGenVersionService versionService;
    private final ContextService contextService;

    @Autowired
    public FormGenController(FormGenJsonLoader FormGenJsonLoader, FormGenMetadataService metadataService, FormGenInstanceService instanceService, FormGenVersionService versionService, ContextService contextService) {
        this.FormGenJsonLoader = FormGenJsonLoader;
        this.metadataService = metadataService;
        this.instanceService = instanceService;
        this.versionService = versionService;
        this.contextService = contextService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/info/get")
    public FormGenMetadataDTO getFormGenInfo(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "contextUri") String contextUri) {

        String key = OWLUtils.createInitialsAndConcatWithSlash(connectionName, contextUri);
        Optional<FormGenMetadata> formGenMetadata = metadataService.findByKey(key);
        if (formGenMetadata.isPresent()) {
            return new FormGenMetadataDTO(formGenMetadata.get());
        } else {
            throw new NotFoundException("FormGenInfo " + key + " not found.");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String getFormGenRawJson(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "contextUri") String contextUri
    ) throws URISyntaxException, JsonProcessingException {

        return FormGenJsonLoader.getFormGenRawJsonAndSaveMetadata(connectionName, contextUri).getRawJson();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/info/update/single")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFormGenInfo(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "contextUri") String contextUri)
            throws URISyntaxException, JsonProcessingException {

        FormGenJsonLoader.getFormGenRawJsonAndSaveMetadata(connectionName, contextUri).getRawJson();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/info/update/batch")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFormGenInfoBatch(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "numberOfUpdates") int numberOfUpdates) {

        log.info("Running batch update on {} contexts of {}.", numberOfUpdates, connectionName);
        contextService.getContexts(connectionName).stream()
                .filter(PredicateUtils.not(Context::isProcessed))
                .limit(numberOfUpdates).forEach(context -> {
            try {
                log.info("Processing {}", context.getUriString());
                FormGenJsonLoader.getFormGenRawJsonAndSaveMetadata(connectionName, context.getUriString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @RequestMapping(method = RequestMethod.GET, path = "/info/contextStats")
    @ResponseStatus(value = HttpStatus.OK)
    public ContextsStatsDTO getContextsStats(
            @RequestParam(value = "connectionName") String connectionName) {

        int totalContexts = contextService.count(connectionName);
        int processedContexts = metadataService.getConnectionCount(connectionName);
        return new ContextsStatsDTO(totalContexts, processedContexts);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/info/formStats")
    @ResponseStatus(value = HttpStatus.OK)
    public FormGenStatsDTO getFormGenStats(
            @RequestParam(value = "connectionName") String connectionName) {

        int formGens = metadataService.getConnectionCount(connectionName);
        int formGenInstances = instanceService.getConnectionCount(connectionName);
        int formGenVersions = versionService.getConnectionCount(connectionName);

        return new FormGenStatsDTO(formGens, formGenVersions, formGenInstances);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/static")
    public String getFormStatic(@RequestParam(value = "contextUri") String contextUri) throws IOException {
        return new String(Files.readAllBytes(ResourceUtils.getFile("classpath:app_generated_form_filled.txt").toPath()));
    }
}
