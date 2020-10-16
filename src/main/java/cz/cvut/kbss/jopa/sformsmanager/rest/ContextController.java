package cz.cvut.kbss.jopa.sformsmanager.rest;

import cz.cvut.kbss.jopa.sformsmanager.service.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contexts")
@RequiredArgsConstructor
public class ContextController {

    private final ContextService contextService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<URI> getContexts() {
        return contextService.findAll();
    }

}
