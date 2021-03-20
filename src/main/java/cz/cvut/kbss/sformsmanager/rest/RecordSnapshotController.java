package cz.cvut.kbss.sformsmanager.rest;


import cz.cvut.kbss.sformsmanager.model.dto.RecordSnapshotDTO;
import cz.cvut.kbss.sformsmanager.service.model.local.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/record/snapshot")
public class RecordSnapshotController {

    private final RecordService recordService;

    @Autowired
    public RecordSnapshotController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RecordSnapshotDTO> listRecordSnapshotsForRecord(@RequestParam(value = "projectName") String projectName, @RequestParam(value = "recordURI") String recordURI) {

        return recordService.findRecordSnapshotsForRecord(projectName, recordURI).stream()
                .map(record -> new RecordSnapshotDTO(
                        record.getUri().toString(),
                        record.getKey(),
                        record.getFormTemplateVersion() != null ? record.getFormTemplateVersion().getKey() : null,
                        record.getFormTemplateVersion() != null ? record.getFormTemplateVersion().getInternalName() : null,
                        record.getRecordSnapshotCreated(),
                        record.getRemoteContextURI().toString(),
                        record.getAnswers() != null ? record.getAnswers().size() : 0 // TODO: very ineffective, should be part of the initial call
                ))
                .collect(Collectors.toList());
    }
}
