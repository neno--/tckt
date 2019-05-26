package com.github.nenomm.tcktc;

import com.github.nenomm.tckt.lib.Ticket;
import com.github.nenomm.tcktc.rest.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

@Component
public class RequestExecutor {
    private static Logger LOG = LoggerFactory.getLogger(RequestExecutor.class);

    @Autowired
    private ClientProperties clientProperties;

    @Autowired
    private RestClient restClient;

    private Set<Thread> workers;

    @PostConstruct
    private void setUp() {
        workers = new HashSet<>(clientProperties.getNumberOfThreads());
    }

    void execute() {
        for (int i = 0; i < clientProperties.getNumberOfThreads(); i++) {
            LOG.info("Starting thread {}/{}", i + 1, clientProperties.getNumberOfThreads());

            Thread worker = new Thread(createTask(i));
            worker.start();

            workers.add(worker);
        }

        workers.forEach(worker -> {
            try {
                worker.join();
            } catch (InterruptedException e) {
                LOG.error("Worker {} interrupted!", worker.getId(), e);
            }
        });
    }

    private Runnable createTask(int taskId) {
        return () -> {
            String threadId = Long.toString(Thread.currentThread().getId());

            LOG.info("Starting {} iterations for client thread {}", clientProperties.getNumberOfIterations(), threadId);

            for (int i = 0; i < clientProperties.getNumberOfIterations(); i++) {
                Ticket clientTicket = restClient.getTicket(threadId);
                LOG.info("Iteration {}-{}, thread {} received {} for clientId {}", i + 1, taskId + 1, threadId, clientTicket, threadId);

                Ticket commonTicket = restClient.getTicket(clientProperties.getCommonClientId());
                LOG.info("Iteration {}-{}, thread {} received {} for clientId {}", i + 1, taskId + 1, threadId, commonTicket, threadId);
            }

            LOG.info("Finished {} iterations for thread {}", clientProperties.getNumberOfIterations(), threadId);
        };
    }
}
