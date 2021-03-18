package cz.cvut.kbss.sformsmanager.rest;


import cz.cvut.kbss.sformsmanager.model.dto.RecordDTO;
import cz.cvut.kbss.sformsmanager.service.model.local.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RecordDTO> listRecords(@RequestParam(value = "projectName") String projectName) {

//        int numberOfRecords = recordService.countRecords(projectName);
//        int numberOfRecordVersions = recordService.countRecordVersions(projectName);

        return recordService.findAllRecords(projectName).stream()
                .map(record -> new RecordDTO(
                        record.getUri().toString(),
                        record.getRecordCreated(),
                        record.getKey(),
                        record.getRemoteContextURI(),
                        0, // TODO:
                        0
                ))
                .collect(Collectors.toList());
    }
}
