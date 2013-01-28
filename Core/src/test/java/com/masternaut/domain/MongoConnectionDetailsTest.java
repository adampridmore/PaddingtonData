package com.masternaut.domain;

import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MongoConnectionDetailsTest {
    @Test
    public void createDefault() throws Throwable {
        String baseUri = "mongodb://MNAB-DEV15L:30000,MNAB-DEV15L:30001,MNAB-DEV15L:30002";
        String systemMongoUri = "mongodb://MNAB-DEV15L:30000,MNAB-DEV15L:30001,MNAB-DEV15L:30002" + "/Test_System";

        Mongo systemMongo = new Mongo(new MongoURI(systemMongoUri));

        String newDatabaseName = "MyNewDatabaseName";
        String clonedMongoUri = PaddingtonMongoUriHelper.createFromMongo(systemMongo, newDatabaseName);

        assertEquals(baseUri + "/" + newDatabaseName, clonedMongoUri);
    }
}
