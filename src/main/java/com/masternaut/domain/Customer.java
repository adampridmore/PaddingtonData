package com.masternaut.domain;

import com.masternaut.Identifiable;

public class Customer extends Identifiable {
    private String name;
    private MongoConnectionDetails domainMongoConnectionDetails;

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
}
