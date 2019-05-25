package com.github.nenomm.tcktc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientProperties {
    private String serverUrl;
    private int numberOfThreads;
    private int numberOfIterations;
    private String commonClientId;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(final String p_serverUrl) {
        serverUrl = p_serverUrl;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(final int p_numberOfThreads) {
        numberOfThreads = p_numberOfThreads;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(final int p_numberOfIterations) {
        numberOfIterations = p_numberOfIterations;
    }

    public String getCommonClientId() {
        return commonClientId;
    }

    public void setCommonClientId(final String p_commonClientId) {
        commonClientId = p_commonClientId;
    }
}
