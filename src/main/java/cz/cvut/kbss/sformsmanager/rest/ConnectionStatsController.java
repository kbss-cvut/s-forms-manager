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

import cz.cvut.kbss.sformsmanager.model.dto.ContextsStatsDTO;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenStatsDTO;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenInstanceService;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenMetadataService;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenVersionService;
import cz.cvut.kbss.sformsmanager.service.model.remote.ContextService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connection/stats")
public class ConnectionStatsController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ConnectionStatsController.class);

    private final FormGenMetadataService metadataService;
    private final FormGenInstanceService instanceService;
    private final FormGenVersionService versionService;
    private final ContextService contextService;

    @Autowired
    public ConnectionStatsController(FormGenMetadataService metadataService, FormGenInstanceService instanceService, FormGenVersionService versionService, ContextService contextService) {
        this.metadataService = metadataService;
        this.instanceService = instanceService;
        this.versionService = versionService;
        this.contextService = contextService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/contexts")
    @ResponseStatus(value = HttpStatus.OK)
    public ContextsStatsDTO getContextsStats(
            @RequestParam(value = "connectionName") String connectionName) {

        int totalContexts = contextService.count(connectionName);
        int processedContexts = metadataService.getConnectionCount(connectionName);
        return new ContextsStatsDTO(totalContexts, processedContexts);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/forms")
    @ResponseStatus(value = HttpStatus.OK)
    public FormGenStatsDTO getFormGenStats(
            @RequestParam(value = "connectionName") String connectionName) {

        int totalContexts = contextService.count(connectionName);
        int processedContexts = metadataService.getConnectionCount(connectionName);
        int formGenInstances = instanceService.getConnectionCount(connectionName);
        int formGenVersions = versionService.getConnectionCount(connectionName);
        int nonEmptyContexts = metadataService.getConnectionNonEmptyCount(connectionName);

        return new FormGenStatsDTO(totalContexts, processedContexts, formGenVersions, formGenInstances, nonEmptyContexts);
    }
}
