package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.List;

public class FormTicketsInCategoriesDTO {

    private final List<TicketDTO> recordTickets;
    private final List<TicketDTO> formVersionTickets;
    private final List<TicketDTO> questionTickets;

    public FormTicketsInCategoriesDTO(List<TicketDTO> recordTickets, List<TicketDTO> formVersionTickets, List<TicketDTO> questionTickets) {
        this.recordTickets = recordTickets;
        this.formVersionTickets = formVersionTickets;
        this.questionTickets = questionTickets;
    }

    public List<TicketDTO> getRecordTickets() {
        return recordTickets;
    }

    public List<TicketDTO> getFormVersionTickets() {
        return formVersionTickets;
    }

    public List<TicketDTO> getQuestionTickets() {
        return questionTickets;
    }
}
