package cz.cvut.kbss.sformsmanager.service.ticketing;

import java.util.List;
import java.util.Map;

public interface TicketingService {

    List<? extends Ticket> findProjectTickets(String projectName);

    Map<String, String> findTicketCustomFields(String cardId);

    /**
     * Return URL of the ticket.
     *
     * @param projectName
     * @param ticket
     * @return URL of the ticket
     */
    String createTicket(String projectName, Ticket ticket);

    TicketToProjectRelations createRelations(String formRelationId, String formVersionRelationId, String questionRelationId);
}
