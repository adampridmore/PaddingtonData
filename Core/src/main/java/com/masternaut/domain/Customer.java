package com.masternaut.domain;

import com.masternaut.Identifiable;

public class Customer extends Identifiable {
    private String name;

    private MongoDatabaseConnectionDetails mongoDatabaseConnectionDetails;

    private long numberOfAssets;
    private long numberOfRouteResults;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MongoDatabaseConnectionDetails getMongoDatabaseConnectionDetails() {
        return mongoDatabaseConnectionDetails;
    }

    public void setMongoDatabaseConnectionDetails(MongoDatabaseConnectionDetails mongoDatabaseConnectionDetails) {
        this.mongoDatabaseConnectionDetails = mongoDatabaseConnectionDetails;
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
