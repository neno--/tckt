package com.github.nenomm.tckts;

import com.github.nenomm.tckt.lib.Ticket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TcktsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcktsApplication.class, args);
        Ticket t = new Ticket();
    }

}
