package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.model.dto.FormGenVersionDTO;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenMetadataService;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenVersionService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formGenVersion")
public class FormGenVersionController {

    private final FormGenMetadataService metadataService;
    private final FormGenVersionService versionService;

    @Autowired
    public FormGenVersionController(FormGenMetadataService metadataService, FormGenVersionService versionService) {
        this.metadataService = metadataService;
        this.versionService = versionService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FormGenVersionDTO> getFormGenVersions(@RequestParam(value = "connectionName") String connectionName) {

        return versionService.findAllInConnection(connectionName).stream()
                .map(version -> new FormGenVersionDTO(
                        version.getVersion(),
                        version.getUri().toString(),
                        version.getSampleContextUri(),
                        metadataService.getConnectionCountByVersion(connectionName, version.getUri().toString())))
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "/histogram")
    public Map<String, Map<Date, Long>> getVersionsHistogramData(@RequestParam(value = "connectionName") String connectionName) {

        return metadataService.findAllInConnection(connectionName).stream()
                .collect(Collectors.groupingBy(p -> p.getFormGenVersion().getVersion(),
                        Collectors.groupingBy(m -> DateUtils.round(m.getFormGenCreated(), Calendar.MONTH)
                                , Collectors.counting())));
    }
}
