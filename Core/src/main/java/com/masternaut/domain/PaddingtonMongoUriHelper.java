package com.masternaut.domain;

import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;

import java.util.List;

public final class PaddingtonMongoUriHelper {
    private PaddingtonMongoUriHelper(){
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
