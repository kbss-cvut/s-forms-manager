package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.model.dto.FormTicketsInCategoriesDTO;
import cz.cvut.kbss.sformsmanager.model.dto.TicketDTO;
import cz.cvut.kbss.sformsmanager.model.persisted.local.RecordSnapshot;
import cz.cvut.kbss.sformsmanager.service.model.local.QuestionTemplateService;
import cz.cvut.kbss.sformsmanager.service.model.local.RecordService;
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

    public static String FORM_VERSION_KEY_CUSTOM_FIELD_ID = "SpecificFormVersionKEY";
    public static String FORM_CONTEXT_URI_CUSTOM_FIELD = "SpecificFormCU";
    public static String QUESTION_ORIGIN_CUSTOM_FIELD = "SpecificQuestionQO";

    private final TicketingService ticketingService;
    private final RecordService recordService;
    private final QuestionTemplateService questionTemplateService;

    @Autowired
    public TicketingController(TicketingService ticketingService, RecordService recordService, QuestionTemplateService questionTemplateService) {
        this.ticketingService = ticketingService;
        this.recordService = recordService;
        this.questionTemplateService = questionTemplateService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/project")
    public List<TicketDTO> getProjectTickets(@RequestParam(value = "projectName") String projectName) {
        return getProjectTicketsStream(projectName).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/version")
    public List<TicketDTO> getVersionTickets(@RequestParam(value = "projectName") String projectName,
                                             @RequestParam(value = "formTemplateVersion") String formTemplateVersion) {
        return getProjectTicketsStream(projectName)
                .filter(ticket -> {
                    String versionCustomFieldValue = ticket.getCustomFields().get(FORM_VERSION_KEY_CUSTOM_FIELD_ID);
                    return versionCustomFieldValue != null && versionCustomFieldValue.equals(formTemplateVersion);
                })
                .collect(Collectors.toList());
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
            String formCustomFieldValue = ticket.getCustomFields().get(FORM_CONTEXT_URI_CUSTOM_FIELD);
            return formCustomFieldValue != null && formCustomFieldValue.equals(recordSnapshot.getRemoteContextURI().toString());
        }).collect(Collectors.toList());
    }

    private List<TicketDTO> getFormTemplateVersionTickets(List<TicketDTO> projectTickets, RecordSnapshot recordSnapshot) {
        return projectTickets.stream().filter(ticket -> {
            String formCustomFieldValue = ticket.getCustomFields().get(FORM_VERSION_KEY_CUSTOM_FIELD_ID);
            return formCustomFieldValue != null && formCustomFieldValue.equals(recordSnapshot.getFormTemplateVersion().getInternalName()) || formCustomFieldValue.equals(recordSnapshot.getFormTemplateVersion().getKey());
        }).collect(Collectors.toList());
    }

    private List<TicketDTO> getQuestionTemplateTickets(List<TicketDTO> projectTickets, RecordSnapshot recordSnapshot, String projectName) {
        List<String> questionTemplates = questionTemplateService.findAllFormTemplateVersionQuestionSnapshots(projectName, recordSnapshot.getFormTemplateVersion().getUri()).stream()
                .map(templateSnapshot -> templateSnapshot.getQuestionOrigin())
                .collect(Collectors.toList());

        return projectTickets.stream().filter(ticket -> {
            String questionCustomFieldValue = ticket.getCustomFields().get(QUESTION_ORIGIN_CUSTOM_FIELD);
            return questionCustomFieldValue != null && questionTemplates.contains(questionCustomFieldValue);
        }).collect(Collectors.toList());
    }

    private Stream<TicketDTO> getProjectTicketsStream(String projectName) {
        return ticketingService.findProjectTickets(projectName).stream()
                .map(card -> {
                    Map<String, String> customFields = ticketingService.findTicketCustomFields(card.getId());
                    return new TicketDTO(card.getName(), card.getDesc(), card.getUrl(), customFields);
                });
    }
}
