package cz.cvut.kbss.sformsmanager.service.ticketing.trello;

import cz.cvut.kbss.sformsmanager.service.ticketing.Ticket;
import cz.cvut.kbss.sformsmanager.service.ticketing.TicketToProjectRelations;

public class TrelloTicket implements Ticket {

    private final String name;

    private final String description;

    private final String url;

    private final TrelloCustomFields customFields;

    public TrelloTicket(String name, String description, String url, TrelloCustomFields customFields) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.customFields = customFields;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public TicketToProjectRelations getTicketCustomRelations() {
        return customFields;
    }
}
