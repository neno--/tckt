package com.github.nenomm.tckts;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server")
public class ServerProperties {
    private String ticketIdFormat;

    public String getTicketIdFormat() {
        return ticketIdFormat;
    }

    public void setTicketIdFormat(String ticketIdFormat) {
        this.ticketIdFormat = ticketIdFormat;
    }
}
