package com.masternaut.domain;

import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;
import org.springframework.util.Assert;

import java.util.List;

public class MongoConnectionDetails {
    private String mongoUri;
    private String databaseName;

    private static final String DEFAULT_LOCAL_MONGOURI = MongoURI.MONGODB_PREFIX +  "localhost:27017";

    @Override
    public String toString() {
        return "MongoConnectionDetails{" +
                "mongoUri='" + mongoUri + '\'' +
                ", databaseName='" + databaseName + '\'' +
                '}';
    }

    public MongoConnectionDetails() {
    }

    public MongoConnectionDetails(String mongoUri, String databaseName) {
        this.mongoUri = mongoUri;
        this.databaseName = databaseName;
    }

    public static MongoConnectionDetails createDefaultLocalConnection(String databaseName) {
        Assert.hasText(databaseName);

        MongoConnectionDetails connectionDetails = new MongoConnectionDetails();
        connectionDetails.databaseName = databaseName;
        connectionDetails.mongoUri=  DEFAULT_LOCAL_MONGOURI;

        return connectionDetails;
    }

    public static MongoConnectionDetails createFromMongo(Mongo mongo, String databaseName) {
        List<ServerAddress> allAddress = mongo.getAllAddress();

        StringBuilder mongoUriString = new StringBuilder();
        mongoUriString.append(MongoURI.MONGODB_PREFIX);

        for(ServerAddress serverAddress : allAddress){
            mongoUriString.append(String.format("%s:%d", serverAddress.getHost(),serverAddress.getPort()));
        }

        return new MongoConnectionDetails(mongoUriString.toString(), databaseName);
    }

    public String getMongoUri() {
        return mongoUri;
    }

    public void setMongoUri(String mongoUri) {
        this.mongoUri = mongoUri;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
