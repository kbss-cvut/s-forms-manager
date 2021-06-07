package cz.cvut.kbss.sformsmanager.service.ticketing;

import com.julienvey.trello.domain.Card;

import java.util.List;
import java.util.Map;

public interface TicketingService {

    List<Card> findProjectTickets(String projectName);

    Map<String, String> findTicketCustomFields(String cardId);
}
