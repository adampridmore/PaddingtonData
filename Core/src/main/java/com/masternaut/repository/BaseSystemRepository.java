package com.masternaut.repository;

import com.masternaut.Identifiable;
import com.masternaut.PaddingtonException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class BaseSystemRepository<T extends Identifiable> {
    protected MongoTemplate mongoTemplate;
    private Class<T> clazz;

    public BaseSystemRepository(MongoTemplate mongoTemplate, Class<T> clazz) {
        this.mongoTemplate = mongoTemplate;
        this.clazz = clazz;
    }

    public void save(T t) {
        mongoTemplate.save(t);
    }

    @Cacheable(value = "repositoryFactory")
    public T findById(String id) {
        T t = mongoTemplate.findById(id, clazz);
        if (t == null) {
            throw new PaddingtonException(String.format("Cannot find %s with id of '%s'", clazz.getSimpleName(), id));
        }

        return t;
    }

    public long count() {
        return mongoTemplate.count(new Query(), clazz);
    }

    public void deleteAll() {
        mongoTemplate.remove(new Query(), clazz);
    }

    public List<T> findAll() {
        return mongoTemplate.findAll(clazz);
    }
}
