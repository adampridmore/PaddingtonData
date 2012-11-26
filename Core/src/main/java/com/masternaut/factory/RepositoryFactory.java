package com.masternaut.factory;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.PaddingtonException;
import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.repository.BaseCustomerRepository;
import com.masternaut.repository.customer.AssetRepository;
import com.masternaut.repository.customer.PersonRepository;
import com.masternaut.repository.customer.RouteResultRepository;
import com.masternaut.repository.system.CustomerRepository;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RepositoryFactory {

    @Autowired
    private MongoTemplate systemMongoTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    public RepositoryFactory() {
    }

    public <T> T createRepository(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public List<String> getDatabaseConnectionInformation() {
        List<String> properties = new ArrayList<String>();

        properties.add(String.format("SystemDatabaseName - %s", systemMongoTemplate.getDb().getName()));

        List<ServerAddress> serverAddressList = systemMongoTemplate.getDb().getMongo().getServerAddressList();
        for (ServerAddress serverAddress : serverAddressList) {
            properties.add(String.format("Host - %s:%d", serverAddress.getHost(), serverAddress.getPort()));
        }

        return properties;
    }

    @Cacheable(value = "customerMongoTemplate")
    public MongoTemplate createMongoTemplateForCustomerId(String customerId) {
        CustomerRepository customerRepository = createRepository(CustomerRepository.class);

        Customer customer = customerRepository.findById(customerId);

        return createMongoTemplate(customer.getDomainMongoConnectionDetails(), customerId);
    }

    public void clearCustomerDatabase() {
        CustomerRepository customerRepository = createRepository(CustomerRepository.class);
        List<Customer> allCustomers = customerRepository.findAll();

        List<BaseCustomerRepository> allCustomerRepositories = new ArrayList<BaseCustomerRepository>();
        allCustomerRepositories.add(createRepository(AssetRepository.class));
        allCustomerRepositories.add(createRepository(RouteResultRepository.class));
        allCustomerRepositories.add(createRepository(PersonRepository.class));

        for (Customer customer : allCustomers) {
            for (BaseCustomerRepository baseCustomerRepository : allCustomerRepositories) {
                baseCustomerRepository.deleteAllForCustomer(customer.getId());
            }
        }
    }

    private <T> PaddingtonDatabase getPaddingtonDatabaseAnnotation(Class<T> clazz) {
        PaddingtonDatabase annotation = clazz.getAnnotation(PaddingtonDatabase.class);
        if (annotation == null) {
            throw new PaddingtonException(String.format("%s is missing %s annotation", clazz, PaddingtonDatabase.class.getSimpleName()));
        }

        return annotation;
    }

    private MongoTemplate createMongoTemplate(MongoConnectionDetails domainMongoConnectionDetails, String customerId) {
        if (domainMongoConnectionDetails == null) {
            throw new PaddingtonException("Invalid domainMongoConnectionDetails on for customer id: " + customerId);
        }

        Mongo mongo;
        try {
            mongo = new Mongo(domainMongoConnectionDetails.getHostname(), domainMongoConnectionDetails.getPort());
        } catch (UnknownHostException e) {
            throw new PaddingtonException(e);
        }

        return new MongoTemplate(mongo, domainMongoConnectionDetails.getDatabaseName());
    }
}
