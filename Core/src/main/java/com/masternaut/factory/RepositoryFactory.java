package com.masternaut.factory;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.PaddingtonException;
import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.repository.system.CustomerRepository;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.net.UnknownHostException;

@Component
public class RepositoryFactory {

    @Autowired
    private MongoTemplate systemMongoTemplate;

    private CustomerRepository customerRepository;

    public <T> T createRepository(Class<T> clazz) {
        if (clazz.equals(CustomerRepository.class)) {
            return (T) getCustomerRepository();
        }

        PaddingtonDatabase databaseAnnotation = getPaddingtonDatabaseAnnotation(clazz);

        if (databaseAnnotation.type() == PaddingtonDatabase.DatabaseType.System) {
            return createSystemRepository(clazz);
        }

        if (databaseAnnotation.type() == PaddingtonDatabase.DatabaseType.CustomerDomain){
            return createCustomerDomainRepository(clazz);
        }

        throw new PaddingtonException("Unknown repository type: " + clazz.getSimpleName());
    }

    private CustomerRepository getCustomerRepository() {
        if (customerRepository == null){
            this.customerRepository = new CustomerRepository(systemMongoTemplate);
        }

        return customerRepository;
    }

    private <T> T createCustomerDomainRepository(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor(RepositoryFactory.class);
            return constructor.newInstance(this);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private <T> T createSystemRepository(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor(MongoTemplate.class);
            return constructor.newInstance(systemMongoTemplate);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private <T> PaddingtonDatabase getPaddingtonDatabaseAnnotation(Class<T> clazz) {
        PaddingtonDatabase annotation = clazz.getAnnotation(PaddingtonDatabase.class);
        if (annotation == null) {
            throw new PaddingtonException(String.format("%s is missing %s annotation", clazz, PaddingtonDatabase.class.getSimpleName()));
        }

        return annotation;
    }

    @Cacheable(value="repositoryFactory")
    public MongoTemplate createMongoTemplateForCustomerId(String customerId) {
        Customer customer = customerRepository.findById(customerId);

        return createMongoTemplate(customer.getDomainMongoConnectionDetails());
    }

    private MongoTemplate createMongoTemplate(MongoConnectionDetails domainMongoConnectionDetails) {
        Mongo mongo;
        try {
            mongo = new Mongo(domainMongoConnectionDetails.getHostname(), domainMongoConnectionDetails.getPort());
        } catch (UnknownHostException e) {
            throw new PaddingtonException(e);
        }

        return new MongoTemplate(mongo, domainMongoConnectionDetails.getDatabaseName());
    }
}