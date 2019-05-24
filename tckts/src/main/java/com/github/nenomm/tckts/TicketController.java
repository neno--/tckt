package com.github.nenomm.tckts;

import com.github.nenomm.tckt.lib.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/ticket")
public class TicketController {
    private static Logger LOG = LoggerFactory.getLogger(TicketController.class);

    @GetMapping
    public Ticket greeting(@RequestParam(value = "clientId") String clientId) {

        Ticket ticket = new Ticket("ticketId", clientId, System.currentTimeMillis());

        LOG.info("Created new ticket {}", ticket);

        return ticket;
    }
}
