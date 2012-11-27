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
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMongoFactory {

    @Autowired
    private MongoTemplate systemMongoTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RouteResultRepository routeResultRepository;

    public List<String> getDatabaseConnectionInformation() {
        List<String> properties = new ArrayList<String>();

        properties.add(String.format("SystemDatabaseName - %s", systemMongoTemplate.getDb().getName()));

        List<ServerAddress> serverAddressList = systemMongoTemplate.getDb().getMongo().getServerAddressList();
        for (ServerAddress serverAddress : serverAddressList) {
            properties.add(String.format("Host - %s:%d", serverAddress.getHost(), serverAddress.getPort()));
        }

        return properties;
    }

    @Cacheable(value = "customerMongoOperations")
    public MongoOperations create(String customerId) {
        Customer customer = customerRepository.findById(customerId);

        return createMongoTemplate(customer.getDomainMongoConnectionDetails(), customerId);
    }

    public void clearCustomerDatabase() {
        List<Customer> allCustomers = customerRepository.findAll();
        List<BaseCustomerRepository> allCustomerRepositories = getAllCustomerSpecificRepositories();

        for (Customer customer : allCustomers) {
            for (BaseCustomerRepository baseCustomerRepository : allCustomerRepositories) {
                baseCustomerRepository.deleteAllForCustomer(customer.getId());
            }
        }
    }

    private List<BaseCustomerRepository> getAllCustomerSpecificRepositories() {
        // TODO - This should use reflection to get all customer repositories.
        List<BaseCustomerRepository> allCustomerRepositories = new ArrayList<BaseCustomerRepository>();
        allCustomerRepositories.add(personRepository);
        allCustomerRepositories.add(assetRepository);
        allCustomerRepositories.add(routeResultRepository);
        return allCustomerRepositories;
    }

    private <T> PaddingtonDatabase getPaddingtonDatabaseAnnotation(Class<T> clazz) {
        PaddingtonDatabase annotation = clazz.getAnnotation(PaddingtonDatabase.class);
        if (annotation == null) {
            throw new PaddingtonException(String.format("%s is missing %s annotation", clazz, PaddingtonDatabase.class.getSimpleName()));
        }

        return annotation;
    }

    private MongoOperations createMongoTemplate(MongoConnectionDetails domainMongoConnectionDetails, String customerId) {
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
