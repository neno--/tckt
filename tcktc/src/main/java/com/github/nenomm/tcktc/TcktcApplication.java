package com.github.nenomm.tcktc;

import com.github.nenomm.tckt.lib.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TcktcApplication implements CommandLineRunner {
    private static Logger LOG = LoggerFactory
            .getLogger(TcktcApplication.class);

    @Autowired
    private RestClient restClient;

    public static void main(String[] args) {

        SpringApplication.run(TcktcApplication.class, args);

    }

    @Override
    public void run(String... args) {
        LOG.info("started");

        //for (int i = 0; i < 10; i++) {
            Ticket ticket = restClient.getTicket(Long.toString(0));
            LOG.info("Received ticket #{} -> {}", 0, ticket);
        //}

        LOG.info("stopped");
    }

}
