package com.github.nenomm.tcktc.rest;

import com.github.nenomm.tckt.lib.Ticket;
import com.github.nenomm.tckt.lib.TicketRequest;
import com.github.nenomm.tcktc.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

// for this use case, RestTemplate can be considered thread safe
// https://stackoverflow.com/questions/22989500/is-resttemplate-thread-safe
@Component
public class RestClient {
    @Autowired
    private ClientProperties clientProperties;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;
    private URI serverUri;


    @PostConstruct
    private void setUp() throws URISyntaxException {
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        serverUri = new URI(clientProperties.getServerUrl());
    }

    public Ticket getTicket(String clientId) {
        return restTemplate.postForObject(serverUri, new TicketRequest(clientId), Ticket.class);
    }
}
