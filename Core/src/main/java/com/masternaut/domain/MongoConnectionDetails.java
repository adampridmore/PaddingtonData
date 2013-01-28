package com.masternaut.domain;

import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;
import org.springframework.util.Assert;

import java.util.List;

public class MongoConnectionDetails {
    private String mongoUri;

    private static final String DEFAULT_LOCAL_MONGOURI = MongoURI.MONGODB_PREFIX +  "localhost:27017";

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MongoConnectionDetails");
        sb.append("{mongoUri='").append(mongoUri).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public MongoConnectionDetails() {
    }

    public MongoConnectionDetails(String mongoUri) {
        this.mongoUri = mongoUri;
    }

    public static MongoConnectionDetails createDefaultLocalConnection(String databaseName) {
        Assert.hasText(databaseName);

        MongoConnectionDetails connectionDetails = new MongoConnectionDetails();
        connectionDetails.mongoUri=  String.format("%s/%s", DEFAULT_LOCAL_MONGOURI, databaseName);


        return connectionDetails;
    }

    public static MongoConnectionDetails createFromMongo(Mongo mongo, String databaseName) {
        List<ServerAddress> allAddress = mongo.getAllAddress();

        StringBuilder mongoUriString = new StringBuilder();
        mongoUriString.append(MongoURI.MONGODB_PREFIX);

        for(ServerAddress serverAddress : allAddress){
            mongoUriString.append(String.format("%s:%d,", serverAddress.getHost(),serverAddress.getPort()));
        }

        if (allAddress.size() > 0){
            mongoUriString.deleteCharAt(mongoUriString.length()-1);
        }

        mongoUriString.append(String.format("/%s", databaseName));

        return new MongoConnectionDetails(mongoUriString.toString());
    }

    public String getMongoUri() {
        return mongoUri;
    }

    public void setMongoUri(String mongoUri) {
        this.mongoUri = mongoUri;
    }

    public MongoURI createMongoUri(){
        return new MongoURI(mongoUri);
    }
}
