package com.masternaut.repository;

import com.masternaut.CustomerIdentifiable;
import com.masternaut.DomainHelper;
import com.masternaut.PaddingtonException;
import com.masternaut.domain.Customer;
import com.masternaut.factory.RepositoryFactory;
import com.masternaut.repository.system.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BaseCustomerRepository<T extends CustomerIdentifiable> {

    @Autowired
    protected RepositoryFactory repositoryFactory;

    private Class<T> clazz;

    public BaseCustomerRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void deleteAllForCustomer(String customerId) {
        MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customerId);

        Criteria criteria = createCriteriaForCustomer(customerId);
        Query query = Query.query(criteria);

        mongoTemplate.remove(query, clazz);
    }

    private Criteria createCriteriaForCustomer(String customerId) {
        return Criteria.where("customerId").is(customerId);
    }

    public void bulkInsert(Iterable<T> tlist) {
        if (!tlist.iterator().hasNext()){
            return;
        }

        ArrayList<T> list = new ArrayList<T>();

        for (T t : tlist) {
            list.add(t);
        }

        String customerId = DomainHelper.getCustomerIdFromList(tlist);

        MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customerId);

        mongoTemplate.insert(list, clazz);
    }

    public void save(T t) {
        if (t.getCustomerId() == null) {
            throw new PaddingtonException(String.format("CustomerId not set on %s", clazz.getSimpleName()));
        }

        MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(t.getCustomerId());

        mongoTemplate.save(t);
    }

    public void save(Iterable<T> tlist) {
        if (!tlist.iterator().hasNext()){
            return;
        }

        for(T t : tlist){
            MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(t.getCustomerId());
            mongoTemplate.save(t);
        }
    }

    public T findById(String id, String customerId) {
        MongoTemplate customerMongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customerId);

        T t = customerMongoTemplate.findById(id, clazz);

        if (t == null) {
            String error = String.format("%s with id of '%s' not found.", clazz.getSimpleName(), id);
            throw new PaddingtonException(error);
        }

        return t;
    }

    public List<T> findAllForCustomer(String customerId) {
        MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customerId);

        Criteria criteria = createCriteriaForCustomer(customerId);

        Query query = Query.query(criteria);

        List<T> entities = mongoTemplate.find(query, clazz);

        for (T t : entities) {
            t.setCustomerId(customerId);
        }

        return entities;
    }

    public long countForAllCustomers() {
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);

        List<Customer> allCustomers = customerRepository.findAll();

        int count = 0;
        for (Customer customer : allCustomers) {
            MongoTemplate customerMongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customer.getId());

            Criteria criteria = createCriteriaForCustomer(customer.getId());

            count += customerMongoTemplate.count(new Query(criteria), clazz);
        }

        return count;
    }

    public long countForCustomerId(String customerId) {
        MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customerId);

        Criteria criteria = createCriteriaForCustomer(customerId);

        return mongoTemplate.count(new Query(criteria), clazz);
    }
}
