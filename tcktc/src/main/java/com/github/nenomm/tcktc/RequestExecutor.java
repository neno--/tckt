package com.github.nenomm.tcktc;

import com.github.nenomm.tckt.lib.Ticket;
import com.github.nenomm.tcktc.rest.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

@Component
public class RequestExecutor {
    private static Logger LOG = LoggerFactory.getLogger(RequestExecutor.class);

    @Autowired
    private RestClient restClient;

    private ExecutorService executor;

    @PostConstruct
    private void setUp() {
        LOG.debug("Creating new thread pool of {} threads", 4);
        executor = Executors.newFixedThreadPool(4);
    }

    void execute() {
        for (int i = 0; i < 4; i++) {
            LOG.info("Starting thread {}/{}", i, 3);
            executor.execute(createTask(i));
        }

        executor.shutdown();
    }

    private Runnable createTask(int taskId) {
        return () -> {
            String threadId = Long.toString(Thread.currentThread().getId());

            LOG.info("Starting {} iterations for client thread {}", 1000, threadId);

            for (int i = 0; i < 1000; i++) {
                Ticket clientTicket = restClient.getTicket("");
                LOG.info("Iteration {}/{}, thread {} received {} for clientId {}", taskId, i, threadId, clientTicket, threadId);

                Ticket commonTicket = restClient.getTicket("common");
                LOG.info("Iteration {}/{}, thread {} received {} for clientId {}", taskId, i, threadId, commonTicket, "common");
            }

            LOG.info("End iterations for thread {}", threadId);
        };
    }
}
