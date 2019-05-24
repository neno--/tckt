package com.github.nenomm.tcktc;

import com.github.nenomm.tckt.lib.Ticket;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

@Component
public class RestClient {
    private RestTemplate restTemplate;

    @PostConstruct
    private void setUp() {
        restTemplate = new RestTemplate();
    }

    public Ticket getTicket(String clientId) {
        return restTemplate.getForObject(constructUrl(clientId), Ticket.class);
    }

    private String constructUrl(String clientId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:8080/ticket/")
                .queryParam("clientId", clientId);

        return builder.toUriString();
    }
}
