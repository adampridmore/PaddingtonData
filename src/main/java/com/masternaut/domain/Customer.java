package com.masternaut.domain;

public class Customer {
    private String id;
    private String name;
    private MongoConnectionDetails domainMongoConnectionDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
