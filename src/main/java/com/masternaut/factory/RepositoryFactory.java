package com.masternaut.factory;

import com.masternaut.PaddingtonException;
import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.repository.AssetRepository;
import com.masternaut.repository.CustomerRepository;
import com.masternaut.repository.PersonRepository;
import com.masternaut.repository.SystemSettingsRepository;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Component
public class RepositoryFactory {
    private MongoTemplate systemMongoTemplate;
    private CustomerRepository customerRepository;

    @Autowired
    public RepositoryFactory(MongoTemplate systemMongoTemplate) {
        this.systemMongoTemplate = systemMongoTemplate;
        this.customerRepository = new CustomerRepository(systemMongoTemplate);
    }

    public <T> T createRepository(Class<T> clazz) {

        Class<CustomerRepository> customerRepositoryClass = CustomerRepository.class;

        if (clazz.equals(customerRepositoryClass)) {
            return (T)customerRepository;
        }

        // TODO - Load either system or customer specific repository here
        if (clazz.equals(SystemSettingsRepository.class)){
            return (T)new SystemSettingsRepository(systemMongoTemplate);
        }

        // TODO - Customer specific repositories
        if (clazz.equals(AssetRepository.class)){
           return (T)new AssetRepository(this);
        }
        if (clazz.equals(PersonRepository.class)){
            return (T)new PersonRepository(this);
        }

        throw new PaddingtonException("Unknown repository type: " + clazz.getSimpleName());
    }

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

        return new MongoTemplate(mongo,  domainMongoConnectionDetails.getDatabaseName());
    }
}
