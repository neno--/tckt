package com.github.nenomm.tckts.config;

import com.github.nenomm.tckts.id.IdGenerator;
import com.github.nenomm.tckts.id.IdGenerator.Type;
import com.github.nenomm.tckts.id.coarselocking.CoarseIdGenerator;
import com.github.nenomm.tckts.id.finelocking.FineIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    public IdGenerator idGenerator() {
        switch (Type.valueOf(serverProperties.getIdGenerator().getType())) {
            case FINE:
                return new FineIdGenerator();
            case COARSE:
                return new CoarseIdGenerator();
            default:
                throw new IllegalStateException("Not implemented");
        }
    }
}
