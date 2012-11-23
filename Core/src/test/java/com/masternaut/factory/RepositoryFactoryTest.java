package com.masternaut.factory;

import com.masternaut.repository.BaseCustomerRepositoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StopWatch;

import static junit.framework.Assert.assertTrue;

public class RepositoryFactoryTest extends BaseCustomerRepositoryTest {
    @Test
    public void cachingTest() {
        MongoTemplate a = repositoryFactory.createMongoTemplateForCustomerId(customer1.getId());
        MongoTemplate b = repositoryFactory.createMongoTemplateForCustomerId(customer1.getId());

        assertTrue(a == b);
    }

    @Test
    public void gettingCustomerRepositoryIsFast() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int numberToCreate = 10000;
        for (int i = 0; i < numberToCreate; i++) {
            MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customer1.getId());

            mongoTemplate.toString();
        }

        stopWatch.stop();

        double expectedDurationSeconds = 3;

        String message = String.format("Expected to take less than %fs but it took %fs.", expectedDurationSeconds, stopWatch.getTotalTimeSeconds());

        Assert.assertTrue(message, stopWatch.getTotalTimeSeconds()< expectedDurationSeconds);

        System.out.println(String.format("Took %f seconds.", stopWatch.getTotalTimeSeconds()));
    }
}
