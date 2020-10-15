package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.service.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contexts")
@RequiredArgsConstructor
public class ContextController {

    private final ContextService contextService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getContextNames(@RequestParam(value = "connectionName") String connectionName) {
        return contextService.findAll(connectionName).stream().map(context -> context.getUri().toString()).collect(Collectors.toList());
    }

}
