package com.masternaut.factory;

import com.masternaut.repository.BaseCustomerRepositoryTest;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import static junit.framework.Assert.assertTrue;

public class RepositoryFactoryTest extends BaseCustomerRepositoryTest{
    @Test
    public void cachingTest(){
        MongoTemplate a = repositoryFactory.createMongoTemplateForCustomerId(testCustomerId);
        MongoTemplate b = repositoryFactory.createMongoTemplateForCustomerId(testCustomerId);

        assertTrue(a==b);
    }
}
