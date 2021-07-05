package cz.cvut.kbss.sformsmanager.rest;


import cz.cvut.kbss.sformsmanager.exception.RecordSnapshotNotFound;
import cz.cvut.kbss.sformsmanager.exception.VersionNotFoundException;
import cz.cvut.kbss.sformsmanager.model.dto.*;
import cz.cvut.kbss.sformsmanager.model.persisted.local.RecordSnapshot;
import cz.cvut.kbss.sformsmanager.model.persisted.local.SubmittedAnswer;
import cz.cvut.kbss.sformsmanager.service.model.local.RecordService;
import cz.cvut.kbss.sformsmanager.service.model.local.SubmittedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/record/snapshot")
public class RecordSnapshotController {

    private final RecordService recordService;
    private final SubmittedAnswerService answerService;

    @Autowired
    public RecordSnapshotController(RecordService recordService, SubmittedAnswerService answerService) {
        this.recordService = recordService;
        this.answerService = answerService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "find")
    public RecordSnapshotDTO getRecordSnapshotByContextUri(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "recordSnapshotContextUri") String recordSnapshotContextUri) {

        return recordService.findRecordSnapshotByContextUri(projectName, URI.create(recordSnapshotContextUri))
                .map(record -> mapRecord(record))
                .orElseThrow(() -> new RecordSnapshotNotFound("RecordSnapshot not found."));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/find/version")
    public FormTemplateVersionDTO getFormTemplateVersionByRecordSnapshot(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "recordSnapshotContextUri") String recordSnapshotContextUri) {

        return recordService.getFormTemplateVersion(projectName, recordSnapshotContextUri)
                .map(version -> new FormTemplateVersionDTO(
                        version.getKey(),
                        version.getInternalName(),
                        version.getUri().toString(),
                        version.getSampleRemoteContextURI(),
                        -1, // TODO: find effective way to do that
                        -1))
                .orElseThrow(() -> new VersionNotFoundException("Version not found for record snapshot context uri: " + recordSnapshotContextUri));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RecordSnapshotDTO> getRecordSnapshotsForRecord(@RequestParam(value = "projectName") String projectName, @RequestParam(value = "recordURI") String recordURI) {

        return recordService.findRecordSnapshotsForRecord(projectName, recordURI).stream()
                .sorted(Comparator.comparing(RecordSnapshot::getRecordSnapshotCreated))
                .map(record -> mapRecord(record))
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/compare")
    public SubmittedAnswersCompareResultDTO compareRecordSnapshotsAnswers(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "contextUri1") String snapshotContextUri1,
            @RequestParam(value = "contextUri2") String snapshotContextUri2) {

        Set<SubmittedAnswer> answers1 = answerService.getRecordSnapshotAnswers(projectName, snapshotContextUri1);
        Set<SubmittedAnswer> answers2 = answerService.getRecordSnapshotAnswers(projectName, snapshotContextUri2);

        Map<String, SubmittedAnswer> questionAnswerMap1 = answers1.stream().collect(Collectors.toMap(SubmittedAnswer::getQuestionOrigin, Function.identity()));

        Integer numberOfUnchangedAnswers = 0;
        List<SubmittedAnswerDTO> rightAnswers = new LinkedList<>();
        List<SubmittedAnswerChangeDTO> changedAnswers = new LinkedList<>();

        for (SubmittedAnswer answer : answers2) {
            String questionOrigin = answer.getQuestionOrigin();
            String answerValue = answer.getTextValue();

            if (!questionAnswerMap1.containsKey(questionOrigin)) {
                rightAnswers.add(new SubmittedAnswerDTO(questionOrigin, answerValue)); // new answer
            } else if (questionAnswerMap1.containsKey(questionOrigin) && !questionAnswerMap1.get(questionOrigin).getTextValue().equals(answerValue)) {
                changedAnswers.add(new SubmittedAnswerChangeDTO(questionOrigin, questionAnswerMap1.get(questionOrigin).getTextValue(), answer.getTextValue())); // add changed answer
            } else {
                numberOfUnchangedAnswers++;
            }
            answers1.remove(questionAnswerMap1.get(questionOrigin)); // remove common questions
        }

        List<SubmittedAnswerDTO> leftAnswers = answers1.stream().map(answer -> new SubmittedAnswerDTO(answer.getQuestionOrigin(), answer.getTextValue())).collect(Collectors.toList());
        // answers1 now only contain answers that are not in answers2
        return new SubmittedAnswersCompareResultDTO(numberOfUnchangedAnswers, leftAnswers, rightAnswers, changedAnswers);
    }

    private RecordSnapshotDTO mapRecord(RecordSnapshot recordSnapshot) { // TODO: struts
        return new RecordSnapshotDTO(
                recordSnapshot.getUri().toString(),
                recordSnapshot.getKey(),
                recordSnapshot.getFormTemplateVersion() != null ? recordSnapshot.getFormTemplateVersion().getKey() : null,
                recordSnapshot.getFormTemplateVersion() != null ? recordSnapshot.getFormTemplateVersion().getInternalName() : null,
                recordSnapshot.getRecordVersion() != null ? recordSnapshot.getRecordVersion().getKey() : null,
                recordSnapshot.getRecordSnapshotCreated(),
                recordSnapshot.getRemoteContextURI().toString(),
                recordSnapshot.getAnswers() != null ? recordSnapshot.getAnswers().size() : 0 // TODO: very ineffective, should be part of the initial call
        );
    }
}
