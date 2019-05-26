package com.github.nenomm.tckts;

import com.github.nenomm.tckt.lib.Ticket;
import com.github.nenomm.tckts.id.Id;
import com.github.nenomm.tckts.id.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/ticket")
public class TicketController {
    private static Logger LOG = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private IdGenerator idGenerator;

    @GetMapping
    public Ticket greeting(@RequestParam(value = "clientId") String clientId) {

        Id id = idGenerator.getNextId(clientId);

        Ticket ticket = new Ticket(id.getId(), clientId, id.getTimeCreated());

        LOG.info("Created new ticket {}", ticket);

        return ticket;
    }
}
