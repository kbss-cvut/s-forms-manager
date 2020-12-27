package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.model.dto.ContextDTO;
import cz.cvut.kbss.sformsmanager.model.dto.Paginated;
import cz.cvut.kbss.sformsmanager.service.ContextService;
import cz.cvut.kbss.sformsmanager.service.FormGenMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contexts")
public class ContextController {

    private final FormGenMetadataService formGenMetadataService;
    private final ContextService contextService;

    @Autowired
    public ContextController(FormGenMetadataService formGenMetadataService, ContextService contextService) {
        this.formGenMetadataService = formGenMetadataService;
        this.contextService = contextService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContextDTO> getContexts(@RequestParam(value = "connectionName") String connectionName) {
        return contextService.getContexts(connectionName).stream().map(ContextDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "/paginated")
    public Paginated<ContextDTO> getPaginatedContexts(@RequestParam(value = "connectionName") String connectionName, Integer offset, Integer limit) {
        int totalItems = contextService.count(connectionName);

        List<ContextDTO> list = contextService.getPaginatedContexts(connectionName, offset, limit).stream().map(ContextDTO::new).collect(Collectors.toList());
        return new Paginated<>(totalItems, offset, limit, list);
    }

}
