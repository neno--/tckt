package com.github.nenomm.tcktc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientProperties {
    private String serverUrl;
    private int numberOfThreads;
    private long numberOfIterations;
    private String commonClientId;
    private boolean validateStatistics;

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

    public boolean isValidateStatistics() {
        return validateStatistics;
    }

    public void setValidateStatistics(final boolean p_validateStatistics) {
        validateStatistics = p_validateStatistics;
    }
}
