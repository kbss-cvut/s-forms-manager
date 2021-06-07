package cz.cvut.kbss.sformsmanager.service.ticketing.trello;

import com.julienvey.trello.domain.Card;
import cz.cvut.kbss.sformsmanager.service.ticketing.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrelloService implements TicketingService {

    @Value("${trello.board-id}")
    private String boardId;

    private final TrelloClientCustomWrapper trelloClient;

    @Autowired
    public TrelloService(TrelloClientCustomWrapper trelloClient) {
        this.trelloClient = trelloClient;
    }

    @Override
    public List<Card> findProjectTickets(String projectName) {
        return trelloClient.getBoardCards(boardId).stream()
                .filter(card -> card.getLabels().stream().anyMatch(label -> label.getName().equals(projectName)))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> findTicketCustomFields(String ticketId) {
        Map<String, TrelloClientCustomWrapper.CustomFieldDefinition> customFieldDefinitions = trelloClient.getCustomFieldDefinitions(boardId);
        return trelloClient.getCardCustomFields(ticketId).stream()
                .map(cf -> new TrelloCustomField(customFieldDefinitions.get(cf.getIdCustomField()).getName(), cf.getValue().getText()))
                .collect(Collectors.toMap(cf -> cf.getName(), cf -> cf.getValue()));
    }
}
