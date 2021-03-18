package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.model.dto.ContextDTO;
import cz.cvut.kbss.sformsmanager.model.dto.Paginated;
import cz.cvut.kbss.sformsmanager.model.persisted.remote.Context;
import cz.cvut.kbss.sformsmanager.service.model.remote.ContextService;
import cz.cvut.kbss.sformsmanager.service.process.RemoteDataProcessingOrchestrator;
import cz.cvut.kbss.sformsmanager.utils.PredicateUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/remote")
public class RemoteDataProcessingController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RemoteDataProcessingController.class);

    private final ContextService contextService;
    private final RemoteDataProcessingOrchestrator processingService;

    @Autowired
    public RemoteDataProcessingController(ContextService contextService, RemoteDataProcessingOrchestrator processingService) {
        this.contextService = contextService;
        this.processingService = processingService;
    }


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContextDTO> getContexts(@RequestParam(value = "projectName") String projectName) {
        return contextService.getContexts(projectName).stream().map(ContextDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "/paginated")
    public Paginated<ContextDTO> getPaginatedContexts(@RequestParam(value = "projectName") String projectName, Integer offset, Integer limit) {
        int totalItems = contextService.count(projectName);

        List<ContextDTO> list = contextService.getPaginatedContexts(projectName, offset, limit).stream().map(ContextDTO::new).collect(Collectors.toList());
        return new Paginated<>(totalItems, offset, limit, list);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/process/single")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFormGenInfo(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "contextUri") String contextUri)
            throws IOException {

        processingService.processDataSnapshotInRemoteContext(projectName, URI.create(contextUri));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/process/batch")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFormGenInfoBatch(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "numberOfUpdates") int numberOfUpdates) {

        log.info("Running batch update on {} contexts of {}.", numberOfUpdates, projectName);
        contextService.getContexts(projectName).stream()
                .filter(PredicateUtils.not(Context::isProcessed))
                .limit(numberOfUpdates).forEach(
                context -> {
                    try {
                        log.info("Processing {}", context.getUriString());
                        processingService.processDataSnapshotInRemoteContext(projectName, URI.create(context.getUriString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
