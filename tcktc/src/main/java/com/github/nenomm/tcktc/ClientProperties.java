package com.github.nenomm.tcktc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientProperties {
    private String serverUrl;
    private int numberOfThreads;
    private long numberOfIterations;
    private String commonClientId;

    @Autowired
    private Statistics statistics;

    @Configuration
    @ConfigurationProperties(prefix = "statistics")
    public class Statistics {
        private boolean active;
        private boolean validate;
        private String ticketIdFormat;

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public boolean isValidate() {
            return validate;
        }

        public void setValidate(boolean validate) {
            this.validate = validate;
        }

        public String getTicketIdFormat() {
            return ticketIdFormat;
        }

        public void setTicketIdFormat(String ticketIdFormat) {
            this.ticketIdFormat = ticketIdFormat;
        }

    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public long getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(long numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public String getCommonClientId() {
        return commonClientId;
    }

    public void setCommonClientId(String commonClientId) {
        this.commonClientId = commonClientId;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
