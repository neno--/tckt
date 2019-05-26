package com.github.nenomm.tcktc.statistics;

import com.github.nenomm.tckt.lib.Ticket;
import com.github.nenomm.tcktc.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.annotation.PostConstruct;

@Component
// This is NOT thread safe object, however, since each thread uses its own WorkerStatistics it is effectively thread safe.
public class Statistics {
    private static Logger LOG = LoggerFactory.getLogger(Statistics.class);

    @Autowired
    private ClientProperties clientProperties;

    private ConcurrentHashMap<String, WorkerStatistics> statistics;

    @PostConstruct
    private void setUp() {
        statistics = new ConcurrentHashMap<>(clientProperties.getNumberOfThreads());
    }

    public void init(String clientId) {
        WorkerStatistics workerStatistics = statistics.putIfAbsent(clientId, new WorkerStatistics(clientProperties.getNumberOfIterations()));

        if (workerStatistics != null) {
            throw new IllegalStateException(String.format("Already started for client %d!", clientId));
        }
    }

    public void start(String clientId) {
        statistics.get(clientId).start();
    }

    public void startCommon(String clientId) {
        statistics.get(clientId).startCommon();
    }

    public void lap(String clientId, Ticket ticket) {
        if (clientProperties.getCommonClientId().equals(ticket.getClientId())) {
            statistics.get(clientId).lapCommon(ticket);
        } else {
            statistics.get(clientId).lap(ticket);
        }
    }

    public void validate() {
        boolean commonValidation = true;
        double commonTotalTimeSeconds = 0;
        List<Ticket> allCommonTickets = new ArrayList((int) Math.min(clientProperties.getNumberOfIterations() * 4, Integer.MAX_VALUE));

        for (Entry<String, WorkerStatistics> entry : statistics.entrySet()) {
            String clientId = entry.getKey();
            WorkerStatistics workerStatistics = entry.getValue();

            if (checkTicketContent(workerStatistics.getClientTickets(), 0, clientProperties.getNumberOfIterations())) {
                LOG.info("Client {} tickets validation OK", clientId);
            } else {
                LOG.error("Client {} tickets validation FAILED", clientId);
            }

            LOG.info("Client {} tickets took {} seconds.", clientId, workerStatistics.getClientStatistics().getTotalTimeSeconds());

            allCommonTickets.addAll(workerStatistics.getCommonTickets());
            commonTotalTimeSeconds += workerStatistics.getCommonStatistics().getTotalTimeSeconds();
        }

        if (checkTicketContent(allCommonTickets, 0, clientProperties.getNumberOfIterations() * 4)) {
            LOG.info("All clients common tickets validation OK");
        } else {
            LOG.error("All client common tickets validation FAILED");
        }

        LOG.info("Client {} took {} seconds.", clientProperties.getCommonClientId(), commonTotalTimeSeconds);
    }

    private boolean checkTicketContent(List<Ticket> tickets, long startInclusive, long endExclusive) {
        // [0, n) -> ["ticket-0", "ticket-n")
        Set<String> expectedIds = LongStream.range(startInclusive, endExclusive).boxed().map(number ->
                String.format(clientProperties.getTicketIdFormat(), number)).collect(Collectors.toSet());

        if (expectedIds.size() != tickets.size()) {
            return false;
        }

        tickets.forEach(ticket -> expectedIds.remove(ticket.getTicketId()));

        return expectedIds.isEmpty();
    }
}