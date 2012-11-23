package com.masternaut.domain;

import com.masternaut.Identifiable;

public class Customer extends Identifiable {
    private String name;
    private MongoConnectionDetails domainMongoConnectionDetails;

    private long numberOfAssets;
    private long numberOfRouteResults;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MongoConnectionDetails getDomainMongoConnectionDetails() {
        return domainMongoConnectionDetails;
    }

    public void setDomainMongoConnectionDetails(MongoConnectionDetails domainMongoConnectionDetails) {
        this.domainMongoConnectionDetails = domainMongoConnectionDetails;
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
