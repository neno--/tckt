package com.github.nenomm.tckt.lib;


import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class Ticket {
    private long ticketId;
    private String clientId;
    private long timeCreated;

    public Ticket() {
        // for jackson
    }

    public Ticket(long ticketId, String clientId, long timeCreated) {
        Validate.inclusiveBetween(0, Long.MAX_VALUE, ticketId, "ticketId must not be negative value");
        Validate.notBlank(clientId, "clientId must not be null or empty");

        this.ticketId = ticketId;
        this.clientId = clientId;
        this.timeCreated = timeCreated;
    }

    public long getTicketId() {
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
        Ticket ticket = (Ticket) o;
        return getTicketId() == ticket.getTicketId() &&
                getTimeCreated() == ticket.getTimeCreated() &&
                getClientId().equals(ticket.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTicketId(), getClientId(), getTimeCreated());
    }
}
