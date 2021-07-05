package cz.cvut.kbss.sformsmanager.rest;


import cz.cvut.kbss.sformsmanager.exception.RecordSnapshotNotFound;
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

    @RequestMapping(method = RequestMethod.GET, path = "find")
    public RecordDTO getRecordByRecordSnapshotKey(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "recordSnapshotKey") String recordSnapshotKey) {

        return recordService.findRecordBySnapshotKey(projectName, recordSnapshotKey)
                .map(record -> new RecordDTO(
                        record.getUri().toString(),
                        record.getRecordCreated(),
                        record.getKey(),
                        record.getRemoteContextURI(),
                        -1, // TODO: find effective way to do that
                        -1
                ))
                .orElseThrow(() -> new RecordSnapshotNotFound("RecordSnapshot not found."));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RecordDTO> listNonEmptyRecords(@RequestParam(value = "projectName") String projectName) {

        return recordService.findAllNonEmptyRecords(projectName).stream()
                .map(record -> new RecordDTO(
                        record.getUri().toString(),
                        record.getRecordCreated(),
                        record.getKey(),
                        record.getRemoteContextURI(),
                        recordService.countRecordSnapshotsForRecord(projectName, record),
                        recordService.countRecordVersionsForRecord(projectName, record) // TODO: very ineffective, should be part of the initial call
                ))
                .collect(Collectors.toList());
    }
}
