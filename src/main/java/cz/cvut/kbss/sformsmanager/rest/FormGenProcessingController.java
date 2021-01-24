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

import cz.cvut.kbss.sformsmanager.model.persisted.remote.Context;
import cz.cvut.kbss.sformsmanager.service.model.remote.ContextService;
import cz.cvut.kbss.sformsmanager.service.process.FormGenProcessingService;
import cz.cvut.kbss.sformsmanager.utils.PredicateUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/formGen/processing")
public class FormGenProcessingController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenProcessingController.class);

    private final ContextService contextService;
    private final FormGenProcessingService processingService;

    @Autowired
    public FormGenProcessingController(ContextService contextService, FormGenProcessingService processingService) {
        this.contextService = contextService;
        this.processingService = processingService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/single")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFormGenInfo(
            @RequestParam(value = "connectionName") String connectionName,
            @RequestParam(value = "contextUri") String contextUri)
            throws IOException {

        processingService.processFormGen(connectionName, contextUri);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/batch")
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
                processingService.processFormGen(connectionName, context.getUriString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
