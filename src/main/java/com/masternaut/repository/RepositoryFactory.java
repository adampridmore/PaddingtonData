package com.masternaut.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

@Component
public class RepositoryFactory {
    private MongoTemplate mongoTemplate;

    @Autowired
    public RepositoryFactory(MongoTemplate mongoTemplate) {

        this.mongoTemplate = mongoTemplate;
    }

    public <T> T createRepository(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor(MongoTemplate.class);
            return constructor.newInstance(mongoTemplate);
        } catch(Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
