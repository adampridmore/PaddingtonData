package com.masternaut.domain;

import java.io.Serializable;

public class MongoDatabaseConnectionDetails implements Serializable {

    private String mongoUri;

    public MongoDatabaseConnectionDetails(String mongoUri) {
        this.mongoUri = mongoUri;
    }

    public MongoDatabaseConnectionDetails() {

    }

    public String getMongoUri() {
        return mongoUri;
    }

    public void setMongoUri(String mongoUri) {
        this.mongoUri = mongoUri;
    }
}
