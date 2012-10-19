package com.masternaut.repository;

import com.masternaut.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public CustomerRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(Customer customer) {
        mongoTemplate.save(customer);

    }

    public Customer findById(String id) {
        return mongoTemplate.findById(id, Customer.class);
    }

    public long count() {
        return mongoTemplate.count(new Query(),Customer.class);
    }

    public void deleteAll() {
        mongoTemplate.remove(new Query(), Customer.class);
    }
}
