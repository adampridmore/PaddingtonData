package com.masternaut.domain;

import org.springframework.util.Assert;

public class MongoConnectionDetails {
    public static final String LOCALHOST = "localhost";
    private String hostname;
    private String databaseName;
    private int port;

    public static final int DEFAULT_PORT = 27017;

    @Override
    public String toString() {
        return "MongoConnectionDetails{" +
                "hostname='" + hostname + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", port=" + port +
                '}';
    }

    public MongoConnectionDetails() {
    }

    public MongoConnectionDetails(String hostname, String databaseName, int port) {
        this.hostname = hostname;
        this.databaseName = databaseName;
        this.port = port;
    }

    public static MongoConnectionDetails createDefaultLocalConnection(String databaseName) {
        Assert.hasText(databaseName);

        MongoConnectionDetails connectionDetails = new MongoConnectionDetails();
        connectionDetails.databaseName = databaseName;
        connectionDetails.hostname = LOCALHOST;
        connectionDetails.port = DEFAULT_PORT;

        return connectionDetails;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
