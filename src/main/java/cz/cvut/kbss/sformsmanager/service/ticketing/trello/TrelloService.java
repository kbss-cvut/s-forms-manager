package cz.cvut.kbss.sformsmanager.service.ticketing.trello;

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
    public List<TrelloTicket> findProjectTickets(String projectName) {
        return trelloClient.getBoardCards(boardId).stream()
                .filter(card -> card.getLabels().stream().anyMatch(label -> label.getName().equals(projectName)))
                .map(card -> {
                    Map<String, String> customFields = findTicketCustomFields(card.getId());
                    TrelloCustomFields fields = new TrelloCustomFields(customFields);

                    return new TrelloTicket(card.getName(), card.getDesc(), card.getUrl(), fields);
                }).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> findTicketCustomFields(String ticketId) {
        Map<String, TrelloClientCustomWrapper.CustomFieldDefinition> customFieldDefinitions = trelloClient.getCustomFieldDefinitions(boardId);
        return trelloClient.getCardCustomFields(ticketId).stream()
                .map(cf -> new TrelloCustomField(customFieldDefinitions.get(cf.getIdCustomField()).getName(), cf.getValue().getText()))
                .collect(Collectors.toMap(cf -> cf.getName(), cf -> cf.getValue()));
    }
}
