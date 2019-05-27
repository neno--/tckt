package com.github.nenomm.tckts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server")
public class ServerProperties {
    private String ticketIdFormat;
    private String commonClientId;

    @Autowired
    private IdGenerator idGenerator;

    @Configuration
    @ConfigurationProperties(prefix = "id-generator")
    public class IdGenerator {
        private int clientIdStoreInitialCapacity;
        private String type;

        public int getClientIdStoreInitialCapacity() {
            return clientIdStoreInitialCapacity;
        }

        public void setClientIdStoreInitialCapacity(int clientStoreInitialCapacity) {
            this.clientIdStoreInitialCapacity = clientStoreInitialCapacity;
        }

        public String getType() {
            return type;
        }

        public void setType(String idGeneratorType) {
            this.type = idGeneratorType;
        }
    }

    public String getTicketIdFormat() {
        return ticketIdFormat;
    }

    public void setTicketIdFormat(String ticketIdFormat) {
        this.ticketIdFormat = ticketIdFormat;
    }

    public String getCommonClientId() {
        return commonClientId;
    }

    public void setCommonClientId(String commonClientId) {
        this.commonClientId = commonClientId;
    }

    public IdGenerator getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }
}
