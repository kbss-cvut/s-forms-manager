package cz.cvut.kbss.sformsmanager.service.ticketing;

import java.util.List;
import java.util.Map;

public interface TicketingService {

    List<? extends Ticket> findProjectTickets(String projectName);

    Map<String, String> findTicketCustomFields(String cardId);
}
