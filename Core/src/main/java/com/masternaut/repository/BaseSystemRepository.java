package com.masternaut.repository;

import com.masternaut.Identifiable;
import com.masternaut.PaddingtonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class BaseSystemRepository<T extends Identifiable> {
    @Autowired
    protected MongoTemplate systemMongoTemplate;

    private Class<T> clazz;

    public BaseSystemRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T t) {
        systemMongoTemplate.save(t);
    }

    @Cacheable(value = "customer")
    public T findById(String id) {
        T t = systemMongoTemplate.findById(id, clazz);
        if (t == null) {
            throw new PaddingtonException(String.format("Cannot find %s with id of '%s'", clazz.getSimpleName(), id));
        }

        return t;
    }

    public long count() {
        return systemMongoTemplate.count(new Query(), clazz);
    }

    public void deleteAll() {
        systemMongoTemplate.remove(new Query(), clazz);
    }

    public List<T> findAll() {
        return systemMongoTemplate.findAll(clazz);
    }
}
