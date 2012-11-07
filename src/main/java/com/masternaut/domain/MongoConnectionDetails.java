package com.masternaut.domain;

public class MongoConnectionDetails {
    public static final String LOCALHOST = "localhost";
    private String hostname;
    private String databaseName;
    private int port;

    public static final int DEFAULT_PORT = 27017;

    public MongoConnectionDetails() {
    }

    public MongoConnectionDetails(String databaseName) {
        this.databaseName = databaseName;

        this.hostname = LOCALHOST;
        this.port = DEFAULT_PORT;
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
