package com.github.nenomm.tckt.lib;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class TicketRequest {
    private String clientId;

    public TicketRequest() {
        // for jackson
    }

    public TicketRequest(String clientId) {
        Validate.notBlank(clientId, "clientId must not be null or empty");
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketRequest)) return false;
        TicketRequest that = (TicketRequest) o;
        return getClientId().equals(that.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientId());
    }

    @Override
    public String toString() {
        return "TicketRequest{" +
                "clientId='" + clientId + '\'' +
                '}';
    }
}
