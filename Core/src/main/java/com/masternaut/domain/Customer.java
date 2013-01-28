package com.masternaut.domain;

import com.masternaut.Identifiable;

public class Customer extends Identifiable {
    private String name;

    private String mongoUri;

    private long numberOfAssets;
    private long numberOfRouteResults;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMongoUri() {
        return mongoUri;
    }

    public void setMongoUri(String mongoUri) {
        this.mongoUri = mongoUri;
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
