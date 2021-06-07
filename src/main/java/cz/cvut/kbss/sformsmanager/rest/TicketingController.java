package cz.cvut.kbss.sformsmanager.rest;

import cz.cvut.kbss.sformsmanager.model.dto.TicketDTO;
import cz.cvut.kbss.sformsmanager.service.ticketing.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ticket")
public class TicketingController {

    private final TicketingService ticketingService;

    @Autowired
    public TicketingController(TicketingService ticketingService) {
        this.ticketingService = ticketingService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/project")
    public List<TicketDTO> getFormGenRawJson(@RequestParam(value = "projectName") String projectName) {
        return ticketingService.findProjectTickets(projectName).stream()
                .map(card -> {
                    Map<String, String> customFields = ticketingService.findTicketCustomFields(card.getId());
                    return new TicketDTO(card.getName(), card.getDesc(), card.getUrl(), customFields);
                })
                .collect(Collectors.toList());
    }
}
