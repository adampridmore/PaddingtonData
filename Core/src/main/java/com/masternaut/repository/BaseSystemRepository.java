package com.masternaut.repository;

import com.masternaut.Identifiable;
import com.masternaut.PaddingtonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

public class BaseSystemRepository<T extends Identifiable> {
    @Autowired
    private MongoTemplate systemMongoTemplate;

    protected MongoTemplate getSystemMongoTemplate() {
        return systemMongoTemplate;
    }

    private Class<T> clazz;

    public BaseSystemRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T...tlist) {
        save(Arrays.asList(tlist));
    }

    public void save(Iterable<T> tlist) {
        for(T t : tlist){
            save(t);
        }
    }

    public void save(T t) {
        systemMongoTemplate.save(t);
    }

    public T findById(String id) {
        T t = tryFindById(id);
        if (t == null) {
            throw new PaddingtonException(String.format("Cannot find %s with id of '%s'", clazz.getSimpleName(), id));
        }

        return t;
    }

    public T tryFindById(String id) {
        return systemMongoTemplate.findById(id, clazz);
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

    public void deleteById(String id) {
        Query query = Query.query(Criteria
                .where("id").is(id));

        systemMongoTemplate.remove(query, clazz);
    }
}
