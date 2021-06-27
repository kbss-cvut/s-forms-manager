package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.model.dto.FormTicketsInCategoriesDTO;
import cz.cvut.kbss.sformsmanager.model.dto.TicketDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.local.QuestionTemplateSnapshot;
import cz.cvut.kbss.sformsmanager.model.persisted.local.RecordSnapshot;
import cz.cvut.kbss.sformsmanager.service.model.local.QuestionTemplateService;
import cz.cvut.kbss.sformsmanager.service.model.local.RecordService;
import cz.cvut.kbss.sformsmanager.service.model.local.TicketRelationsService;
import cz.cvut.kbss.sformsmanager.service.ticketing.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/ticket")
public class TicketingController {


    private final TicketingService ticketingService;
    private final RecordService recordService;
    private final TicketRelationsService ticketRelationsService;
    private final QuestionTemplateService questionTemplateService;

    @Autowired
    public TicketingController(TicketingService ticketingService, RecordService recordService, TicketRelationsService ticketRelationsService, QuestionTemplateService questionTemplateService) {
        this.ticketingService = ticketingService;
        this.recordService = recordService;
        this.ticketRelationsService = ticketRelationsService;
        this.questionTemplateService = questionTemplateService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/project")
    public List<TicketDTO> getProjectTickets(@RequestParam(value = "projectName") String projectName) {
        return getProjectTicketsStream(projectName).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/category")
    public FormTicketsInCategoriesDTO getTicketsInCategories(@RequestParam(value = "projectName") String projectName,
                                                             @RequestParam(value = "contextUri") String contextUri) {

        RecordSnapshot recordSnapshot = recordService.findRecordSnapshot(projectName, URI.create(contextUri))
                .orElseThrow(() -> new ResourceNotFoundException("Record with context uri " + contextUri + " not found"));

        List<TicketDTO> projectTickets = getProjectTicketsStream(projectName).collect(Collectors.toList());

        List<TicketDTO> formTickets = getRecordSnapshotTickets(projectTickets, recordSnapshot);
        List<TicketDTO> formVersionTickets = getFormTemplateVersionTickets(projectTickets, recordSnapshot);
        List<TicketDTO> questionTickets = getQuestionTemplateTickets(projectTickets, recordSnapshot, projectName);

        return new FormTicketsInCategoriesDTO(formTickets, formVersionTickets, questionTickets);
    }

    private List<TicketDTO> getRecordSnapshotTickets(List<TicketDTO> projectTickets, RecordSnapshot recordSnapshot) {
        return projectTickets.stream().filter(ticket -> {
            String formRelationId = ticket.getRelations().getRelatedForm();
            return formRelationId != null && formRelationId.equals(recordSnapshot.getRemoteContextURI().toString());
        }).collect(Collectors.toList());
    }

    private List<TicketDTO> getFormTemplateVersionTickets(List<TicketDTO> projectTickets, RecordSnapshot recordSnapshot) {
        return projectTickets.stream().filter(ticket -> {
            String formVersionRelationId = ticket.getRelations().getRelatedFormVersion();

            // check if the ticket version relation id is either the traditional FormTemplateVersion id or its internal name
            return formVersionRelationId != null
                    && (formVersionRelationId.equals(recordSnapshot.getFormTemplateVersion().getInternalName()) //
                    || formVersionRelationId.equals(recordSnapshot.getFormTemplateVersion().getKey()));
        }).collect(Collectors.toList());
    }

    private List<TicketDTO> getQuestionTemplateTickets(List<TicketDTO> projectTickets, RecordSnapshot recordSnapshot, String projectName) {
        Map<String, String> questionTemplates = questionTemplateService.findAllFormTemplateVersionQuestionSnapshots(projectName, recordSnapshot.getFormTemplateVersion().getUri())
                .stream().collect(Collectors.toMap(q -> q.getQuestionOriginPath(), q -> q.getLabel(), (a, b) -> a));

        // check if the question origin path corresponds to the record snapshot form version
        return projectTickets.stream().filter(ticket -> {
            String ticketQOP = ticket.getRelations().getRelatedQuestionOriginPath();
            return ticketQOP != null && questionTemplates.containsKey(ticketQOP);
        }).collect(Collectors.toList());
    }

    private Stream<TicketDTO> getProjectTicketsStream(String projectName) {
        return ticketingService.findProjectTickets(projectName).stream()
                .map(ticket -> {
                    String qtsLabel = ticketRelationsService.getTicketQuestionTemplateSnapshot(projectName, ticket.getTicketCustomRelations())
                            .map(QuestionTemplateSnapshot::getLabel)
                            .orElse(null);
                    TicketDTO.TicketRelationsDTO relationsDTO = TicketDTO.TicketRelationsDTO.createFormTicketRelations(ticket.getTicketCustomRelations(), qtsLabel);
                    return new TicketDTO(ticket.getName(), ticket.getDescription(), ticket.getUrl(), relationsDTO);
                });
    }

}
