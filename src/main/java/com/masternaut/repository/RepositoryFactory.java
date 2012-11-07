package com.masternaut.repository;

import com.masternaut.PaddingtonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {
    private MongoTemplate systemMongoTemplate;

    @Autowired
    public RepositoryFactory(MongoTemplate systemMongoTemplate) {
        this.systemMongoTemplate = systemMongoTemplate;
    }

    public <T> T createRepository(Class<T> clazz) {

        Class<CustomerRepository> customerRepositoryClass = CustomerRepository.class;

        if (clazz.equals(customerRepositoryClass)) {
            return (T)new CustomerRepository(systemMongoTemplate);
        }

        if (clazz.equals(AssetRepository.class)){
           return (T)new AssetRepository(this);
        }

        throw new PaddingtonException("Unknown repository type: " + clazz.getSimpleName());
    }

    public MongoTemplate createMongoTemplateForCustomerId(String customerId) {
        // TODO
        return systemMongoTemplate;
    }
}
