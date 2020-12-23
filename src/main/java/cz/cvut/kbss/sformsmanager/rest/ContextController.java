package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.model.dto.ContextDTO;
import cz.cvut.kbss.sformsmanager.service.ContextService;
import cz.cvut.kbss.sformsmanager.service.FormGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contexts")
public class ContextController {

    private final ContextService contextService;
    private final FormGenService formGenService;

    @Autowired
    public ContextController(ContextService contextService, FormGenService formGenService) {
        this.contextService = contextService;
        this.formGenService = formGenService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContextDTO> getContexts(@RequestParam(value = "connectionName") String connectionName) {
        Set<String> processedContexts = formGenService.findProcessedContexts(connectionName);
        return contextService.findAll(connectionName).stream()
                .map(context -> {
                    String contextName = context.getUri().toString();
                    return new ContextDTO(contextName, processedContexts.contains(contextName));
                })
                .collect(Collectors.toList());
    }

}
