package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.List;

public class FormTicketsInCategoriesDTO {

    private final List<TicketDTO> formTickets;
    private final List<TicketDTO> formVersionTickets;
    private final List<TicketDTO> questionTickets;

    public FormTicketsInCategoriesDTO(List<TicketDTO> formTickets, List<TicketDTO> formVersionTickets, List<TicketDTO> questionTickets) {
        this.formTickets = formTickets;
        this.formVersionTickets = formVersionTickets;
        this.questionTickets = questionTickets;
    }

    public List<TicketDTO> getFormTickets() {
        return formTickets;
    }

    public List<TicketDTO> getFormVersionTickets() {
        return formVersionTickets;
    }

    public List<TicketDTO> getQuestionTickets() {
        return questionTickets;
    }
}
