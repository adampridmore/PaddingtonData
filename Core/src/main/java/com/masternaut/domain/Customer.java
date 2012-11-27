package com.masternaut.domain;

import com.masternaut.Identifiable;

public class Customer extends Identifiable {
    private String name;
    private String databaseName;

    private long numberOfAssets;
    private long numberOfRouteResults;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public long getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(long numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public long getNumberOfRouteResults() {
        return numberOfRouteResults;
    }

    public void setNumberOfRouteResults(long numberOfRouteResults) {
        this.numberOfRouteResults = numberOfRouteResults;
    }
}
