package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.exception.VersionNotFoundException;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormTemplateVersion;
import cz.cvut.kbss.sformsmanager.model.persisted.local.QuestionTemplateSnapshot;
import cz.cvut.kbss.sformsmanager.model.persisted.local.RecordSnapshot;
import cz.cvut.kbss.sformsmanager.service.ticketing.TicketProjectRelations;
import cz.cvut.kbss.sformsmanager.service.ticketing.TicketingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class TicketRelationsService {
    private final TicketingService ticketingService;
    private final RecordService recordService;
    private final FormTemplateService formTemplateService;
    private final QuestionTemplateService questionTemplateService;

    public TicketRelationsService(TicketingService ticketingService, RecordService recordService, FormTemplateService formTemplateService, QuestionTemplateService questionTemplateService) {
        this.ticketingService = ticketingService;
        this.recordService = recordService;
        this.formTemplateService = formTemplateService;
        this.questionTemplateService = questionTemplateService;
    }

    @Transactional
    public Optional<QuestionTemplateSnapshot> getTicketQuestionTemplateSnapshot(String projectName, TicketProjectRelations ticketProjectRelations) {
        if (ticketProjectRelations.getRelatedQuestionOriginPath() == null) {
            return Optional.empty();
        }
        List<QuestionTemplateSnapshot> questionTemplateSnapshots = questionTemplateService.findByQOP(projectName, ticketProjectRelations.getRelatedQuestionOriginPath());
        if (questionTemplateSnapshots.isEmpty()) {
            return Optional.empty();
        }

        Optional<FormTemplateVersion> formTemplateVersionOpt = Optional.empty();

        // if there's a form template version id specified, use it directly
        if (ticketProjectRelations.getRelatedFormVersion() != null) {
            formTemplateVersionOpt = formTemplateService.findVersionByKey(projectName, ticketProjectRelations.getRelatedFormVersion());
        }

        // if there's just the record snapshot identifier, use its form template version instead
        if (!formTemplateVersionOpt.isPresent() && ticketProjectRelations.getRelatedRecordSnapshot() != null) {
            ticketProjectRelations.getRelatedRecordSnapshot();

            Optional<RecordSnapshot> recordSnapshotOpt = recordService.findRecordSnapshot(projectName, URI.create(ticketProjectRelations.getRelatedRecordSnapshot()));
            if (recordSnapshotOpt.isPresent()) {
                formTemplateVersionOpt = Optional.ofNullable(recordSnapshotOpt.get().getFormTemplateVersion());
            }
        }

        // if FormTemplateVersion is not recognized at this point, take any from the project
        FormTemplateVersion formTemplateVersion = formTemplateVersionOpt.orElseGet(() ->
                formTemplateService.findAllVersions(projectName).stream()
                        .findAny()
                        .orElseThrow(() -> new VersionNotFoundException("No FormTemplateVersion is found at the project.")));

        return Optional.of(questionTemplateSnapshots.stream()
                .filter(qts -> qts.getFormTemplateVersion().equals(formTemplateVersion))
                .findAny()
                .orElseGet(() -> questionTemplateSnapshots.get(0)));
    }

}
