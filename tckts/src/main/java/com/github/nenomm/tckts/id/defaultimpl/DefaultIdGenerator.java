package com.github.nenomm.tckts.id.defaultimpl;

import com.github.nenomm.tckts.id.Id;
import com.github.nenomm.tckts.id.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

@Component
public class DefaultIdGenerator implements IdGenerator {

    private ConcurrentHashMap<String, Counter> counters;

    @PostConstruct
    private void setUp() {
        counters = new ConcurrentHashMap<>(4);
    }


    @Override
    public Id getNextId(String clientId) {
        Counter counter = counters.getOrDefault(clientId, new Counter());
        Id id;

        synchronized (counter) {
            counters.putIfAbsent(clientId, counter);
            counter.inc();
            // use lastActivity value for ID's timeCreated to ease correlation and analysis
            id = new Id(counter.getCount(), counter.getLastActivity());
        }

        return id;
    }
}
