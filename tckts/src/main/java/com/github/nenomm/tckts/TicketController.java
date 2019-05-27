package com.github.nenomm.tckts;

import com.github.nenomm.tckt.lib.InvalidTicketRequestException;
import com.github.nenomm.tckt.lib.Ticket;
import com.github.nenomm.tckt.lib.TicketRequest;
import com.github.nenomm.tckts.config.ServerProperties;
import com.github.nenomm.tckts.id.Id;
import com.github.nenomm.tckts.id.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/ticket")
public class TicketController {
    private static Logger LOG = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private IdGenerator idGenerator;

    @PostMapping
    public Ticket createTicket(@RequestBody TicketRequest ticketRequest) {
        validate(ticketRequest);

        Id id = idGenerator.getNextId(ticketRequest.getClientId());
        String ticketId = String.format(serverProperties.getTicketIdFormat(), id.getId());

        Ticket ticket = new Ticket(ticketId, ticketRequest.getClientId(), id.getTimeCreated());

        LOG.info("Created new ticket {}", ticket);

        return ticket;
    }

    // can be either threadId (positive long) or predefined string
    private void validate(TicketRequest ticketRequest) {
        long number;

        try {
            number = Long.parseLong(ticketRequest.getClientId());
        } catch (NumberFormatException e) {
            if (!serverProperties.getCommonClientId().equals(ticketRequest.getClientId())) {
                throw new InvalidTicketRequestException("invalid client Id");
            }
            return;
        }

        if (number <= 0) {
            throw new InvalidTicketRequestException("Invalid client id");
        }
    }
}
