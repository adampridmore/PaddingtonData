package com.masternaut.repository;

import com.masternaut.CustomerIdentifiable;
import com.masternaut.PaddingtonException;
import com.masternaut.factory.RepositoryFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class BaseCustomerRepository<T extends CustomerIdentifiable> {
    private RepositoryFactory repositoryFactory;
    private Class<T> clazz;

    public BaseCustomerRepository(RepositoryFactory repositoryFactory, Class<T> clazz) {
        this.repositoryFactory = repositoryFactory;
        this.clazz = clazz;
    }

    public void deleteAll(String customerId) {
        MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customerId);

        mongoTemplate.remove(new Query(), clazz);
    }

    public void save(T t) {
        if (t.getCustomerId() == null){
            throw new PaddingtonException(String.format("CustomerId not set on %s", clazz.getSimpleName()));
        }

        MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(t.getCustomerId());

        mongoTemplate.save(t);
    }

    public T findById(String id, String customerId) {
        MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customerId);

        T t = mongoTemplate.findById(id, clazz);

        t.setCustomerId(customerId);

        return t;
    }

    public List<T> findAllForCustomer(String customerId) {
        MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customerId);

        List<T> entities = mongoTemplate.findAll(clazz);

        for(T t : entities){
            t.setCustomerId(customerId);
        }

        return entities;
    }
}
