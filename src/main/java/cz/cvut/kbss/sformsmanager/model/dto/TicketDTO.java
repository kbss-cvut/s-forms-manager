package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.Map;

public class TicketDTO {

    private final String name;

    private final String description;

    private final String url;

    private final Map<String, String> customFields;

    public TicketDTO(String name, String description, String url, Map<String, String> customFields) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.customFields = customFields;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public String getUrl() {
        return url;
    }
}
