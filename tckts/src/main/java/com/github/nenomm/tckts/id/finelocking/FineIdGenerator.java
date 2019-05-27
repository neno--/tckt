package com.github.nenomm.tckts.id.finelocking;

import com.github.nenomm.tckts.config.ServerProperties;
import com.github.nenomm.tckts.id.Counter;
import com.github.nenomm.tckts.id.Id;
import com.github.nenomm.tckts.id.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

@Component
public class FineIdGenerator implements IdGenerator {
    private static Logger LOG = LoggerFactory.getLogger(FineIdGenerator.class);

    @Autowired
    private ServerProperties serverProperties;

    private ConcurrentHashMap<String, Counter> counters;

    @PostConstruct
    private void setUp() {
        counters = new ConcurrentHashMap<>(serverProperties.getIdGenerator().getClientIdStoreInitialCapacity());
    }


    @Override
    public Id getNextId(String clientId) {
        Counter counter = counters.getOrDefault(clientId, new Counter());

        synchronized (counter) {
            counters.putIfAbsent(clientId, counter);
            counter.inc();

            LOG.info("Generated id {} at {} for client {}", counter.getCount(), counter.getLastActivity(), clientId);

            // use lastActivity value for ID's timeCreated to ease correlation and analysis
            return new Id(counter.getCount(), counter.getLastActivity());
        }
    }
}
