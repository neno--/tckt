package com.github.nenomm.tckt.lib;


import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class Ticket {
    private String ticketId;
    private String clientId;
    private long timeCreated;

    public Ticket(String ticketId, String clientId, long timeCreated) {
        Validate.notBlank(ticketId, "ticketId must not be null or empty");
        Validate.notBlank(clientId, "clientId must not be null or empty");

        this.ticketId = ticketId;
        this.clientId = clientId;
        this.timeCreated = timeCreated;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getClientId() {
        return clientId;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        final Ticket ticket = (Ticket) o;
        return getTimeCreated() == ticket.getTimeCreated() &&
                getTicketId().equals(ticket.getTicketId()) &&
                getClientId().equals(ticket.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTicketId(), getClientId(), getTimeCreated());
    }
}
