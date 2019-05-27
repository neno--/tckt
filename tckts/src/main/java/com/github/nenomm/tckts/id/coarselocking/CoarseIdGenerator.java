package com.github.nenomm.tckts.id.coarselocking;

import com.github.nenomm.tckts.config.ServerProperties;
import com.github.nenomm.tckts.id.Id;
import com.github.nenomm.tckts.id.IdGenerator;
import com.github.nenomm.tckts.id.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

public class CoarseIdGenerator implements IdGenerator {
    private static Logger LOG = LoggerFactory.getLogger(CoarseIdGenerator.class);

    @Autowired
    private ServerProperties serverProperties;

    private Map<String, Counter> counters;

    @PostConstruct
    private void setUp() {
        counters = new HashMap<>(serverProperties.getIdGenerator().getClientIdStoreInitialCapacity());
    }


    @Override
    public synchronized Id getNextId(String clientId) {
        Counter counter = counters.getOrDefault(clientId, new Counter());

        counters.putIfAbsent(clientId, counter);
        counter.inc();

        LOG.info("Generated id {} at {} for client {}", counter.getCount(), counter.getLastActivity(), clientId);

        // use lastActivity value for ID's timeCreated to ease correlation and analysis
        return new Id(counter.getCount(), counter.getLastActivity());
    }
}
