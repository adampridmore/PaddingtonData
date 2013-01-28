package com.masternaut.domain;

import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;
import org.springframework.util.Assert;

import java.util.List;

public final class PaddingtonMongoUriHelper {
    private PaddingtonMongoUriHelper(){

    }

    private static final String DEFAULT_LOCAL_MONGOURI = MongoURI.MONGODB_PREFIX +  "localhost:27017";

    public static String createDefaultLocalConnection(String databaseName) {
        Assert.hasText(databaseName);

        return String.format("%s/%s", DEFAULT_LOCAL_MONGOURI, databaseName);
    }

    public static String createFromMongo(Mongo mongo, String databaseName) {
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

        return mongoUriString.toString();
    }
}
