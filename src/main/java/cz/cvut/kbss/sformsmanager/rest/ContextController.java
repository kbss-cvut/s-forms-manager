package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.service.ContextService;
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

    @Autowired
    public ContextController(ContextService contextService) {
        this.contextService = contextService;
    }

    private final ContextService contextService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getContextNames(@RequestParam(value = "connectionName") String connectionName) {
        return contextService.findAll(connectionName).stream().map(context -> context.getUri().toString()).collect(Collectors.toList());
    }

}
