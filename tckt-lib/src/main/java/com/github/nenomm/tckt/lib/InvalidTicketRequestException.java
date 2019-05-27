package com.github.nenomm.tckt.lib;

public class InvalidTicketRequestException extends RuntimeException {

    public InvalidTicketRequestException(String errorMessage) {
        super(errorMessage);
    }
}
