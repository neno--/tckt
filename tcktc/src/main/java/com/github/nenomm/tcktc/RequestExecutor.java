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
    private ClientProperties clientProperties;

    @Autowired
    private RestClient restClient;

    private ExecutorService executor;

    @PostConstruct
    private void setUp() {
        LOG.debug("Creating new thread pool of {} threads", clientProperties.getNumberOfThreads());
        executor = Executors.newFixedThreadPool(clientProperties.getNumberOfThreads());
    }

    void execute() {
        for (int i = 0; i < clientProperties.getNumberOfThreads(); i++) {
            LOG.info("Starting thread {}/{}", i + 1, clientProperties.getNumberOfThreads());
            executor.execute(createTask(i));
        }

        executor.shutdown();
    }

    private Runnable createTask(int taskId) {
        return () -> {
            String threadId = Long.toString(Thread.currentThread().getId());

            LOG.info("Starting {} iterations for client thread {}", clientProperties.getNumberOfIterations(), threadId);

            for (int i = 0; i < clientProperties.getNumberOfIterations(); i++) {
                Ticket clientTicket = restClient.getTicket(threadId);
                LOG.info("Iteration {}/{}, thread {} received {} for clientId {}", taskId, i + 1, threadId, clientTicket, threadId);

                Ticket commonTicket = restClient.getTicket(clientProperties.getCommonClientId());
                LOG.info("Iteration {}/{}, thread {} received {} for clientId {}", taskId, i + 1, threadId, commonTicket, restClient.getTicket(clientProperties.getCommonClientId()));
            }

            LOG.info("Finished {} iterations for thread {}", clientProperties.getNumberOfIterations(), threadId);
        };
    }
}
