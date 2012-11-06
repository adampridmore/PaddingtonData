package com.masternaut.repository;

import com.masternaut.domain.Customer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CustomerRepository {
    private MongoTemplate mongoTemplate;

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
        return mongoTemplate.count(new Query(), Customer.class);
    }

    public void deleteAll() {
        mongoTemplate.remove(new Query(), Customer.class);
    }

    public Customer tryFindByName(String customerName) {
        Criteria criteria = Criteria.where("name").is(customerName);

        Query query = new Query(criteria);

        List<Customer> customers = mongoTemplate.find(query, Customer.class);

        if (customers.size() == 0){
            return null;
        }

        if (customers.size() > 1){
            throw new RuntimeException(String.format("Too many customers found with name: '%s'.",customerName));
        }

        return customers.get(0);
    }
}
