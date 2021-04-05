package cz.cvut.kbss.sformsmanager.rest;


import cz.cvut.kbss.sformsmanager.model.dto.RecordSnapshotDTO;
import cz.cvut.kbss.sformsmanager.model.dto.SubmittedAnswerChangeDTO;
import cz.cvut.kbss.sformsmanager.model.dto.SubmittedAnswerDTO;
import cz.cvut.kbss.sformsmanager.model.dto.SubmittedAnswersCompareResultDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.RecordSnapshot;
import cz.cvut.kbss.sformsmanager.model.persisted.local.gen2.SubmittedAnswer;
import cz.cvut.kbss.sformsmanager.service.model.local.RecordService;
import cz.cvut.kbss.sformsmanager.service.model.local.SubmittedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = RequestMethod.GET)
    public List<RecordSnapshotDTO> listRecordSnapshotsForRecord(@RequestParam(value = "projectName") String projectName, @RequestParam(value = "recordURI") String recordURI) {

        return recordService.findRecordSnapshotsForRecord(projectName, recordURI).stream()
                .sorted(Comparator.comparing(RecordSnapshot::getRecordSnapshotCreated))
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

}
