package cz.cvut.kbss.sformsmanager.service.ticketing;

import java.util.List;

public interface Ticket {

    String getName();

    String getDescription();

    String getUrl();

    List<CustomField> getCustomFields();

}
