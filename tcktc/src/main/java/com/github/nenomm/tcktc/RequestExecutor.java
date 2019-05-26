package com.github.nenomm.tcktc;

import com.github.nenomm.tckt.lib.Ticket;
import com.github.nenomm.tcktc.rest.RestClient;
import com.github.nenomm.tcktc.statistics.Statistics;
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

    @Autowired
    private Statistics statistics;

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

        runStatistics(() -> statistics.validate());
    }

    private Runnable createTask(int taskId) {
        return () -> {
            String threadId = Long.toString(Thread.currentThread().getId());

            LOG.info("Starting {} iterations for client thread {}", clientProperties.getNumberOfIterations(), threadId);
            runStatistics(() -> statistics.init(threadId));

            for (int i = 0; i < clientProperties.getNumberOfIterations(); i++) {

                runStatistics(() -> statistics.start(threadId));
                Ticket clientTicket = restClient.getTicket(threadId);
                runStatistics(() -> statistics.lap(threadId, clientTicket));
                LOG.info("Iteration {}-{}, thread {} received {} for clientId {}", i + 1, taskId + 1, threadId, clientTicket, threadId);


                runStatistics(() -> statistics.startCommon(threadId));
                Ticket commonTicket = restClient.getTicket(clientProperties.getCommonClientId());
                runStatistics(() -> statistics.lap(threadId, commonTicket));
                LOG.info("Iteration {}-{}, thread {} received {} for clientId {}", i + 1, taskId + 1, threadId, commonTicket, threadId);
            }

            LOG.info("Finished {} iterations for thread {}", clientProperties.getNumberOfIterations(), threadId);
        };
    }

    // Runnable is run from the same thread.
    private void runStatistics(Runnable runnable) {
        if (clientProperties.isValidateStatistics()) {
            runnable.run();
        }
    }
}
