package com.github.nenomm.tcktc.rest;

import com.github.nenomm.tckt.lib.Ticket;
import com.github.nenomm.tcktc.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

@Component
public class RestClient {
    private static final String CLIENT_ID_PARAMETER = "clientId";

    @Autowired
    private ClientProperties clientProperties;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;


    @PostConstruct
    private void setUp() {
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    public Ticket getTicket(String clientId) {
        return restTemplate.getForObject(constructUrl(clientId), Ticket.class);
    }

    private String constructUrl(String clientId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(clientProperties.getServerUrl())
                .queryParam(CLIENT_ID_PARAMETER, clientId);

        return builder.toUriString();
    }
}
