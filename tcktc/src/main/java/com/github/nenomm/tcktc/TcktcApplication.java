package com.github.nenomm.tcktc;

import com.github.nenomm.tckt.lib.Ticket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TcktcApplication {

    public static void main(String[] args) {

        SpringApplication.run(TcktcApplication.class, args);
        Ticket t = new Ticket();

    }

}
