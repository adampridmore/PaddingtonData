package com.masternaut.factory;

import com.masternaut.PaddingtonException;
import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.repository.BaseCustomerRepository;
import com.masternaut.repository.system.CustomerRepository2;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CustomerMongoFactory {

    @Autowired
    private MongoTemplate systemMongoTemplate;

    @Autowired
    private MongoTemplate customersSharedMongoTemplate;

    @Autowired
    private Mongo systemMongo;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CustomerRepository2 customerRepository;

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

        return createMongoTemplate(customer.getMongoConnectionDetails());
    }

    public void clearCustomerDatabase() {
        // TODO - This would be more efficient if it cleared all unique customer databases for all customers.
        List<Customer> allCustomers = customerRepository.findAll();

        Iterable<BaseCustomerRepository> allCustomerRepositories = getAllCustomerSpecificRepositories();

        for (Customer customer : allCustomers) {
            for (BaseCustomerRepository baseCustomerRepository : allCustomerRepositories) {
                baseCustomerRepository.deleteAllForCustomer(customer.getId());
            }
        }
    }

    private Iterable<BaseCustomerRepository> getAllCustomerSpecificRepositories() {
        Map<String, BaseCustomerRepository> beansOfType = applicationContext.getBeansOfType(BaseCustomerRepository.class);

        return beansOfType.values();
    }

    private MongoOperations createMongoTemplate(MongoConnectionDetails connectionDetails) {
        if (connectionDetails == null){
            return customersSharedMongoTemplate;
        }

        Mongo mongo = createMongo(connectionDetails.getMongoUri());

        return new MongoTemplate(mongo, connectionDetails.getDatabaseName());
    }

    private Mongo createMongo(String mongoUri) {
        // TODO - Cacheing / pooling?
        // Or can/should we use the MongoDBFactory?

        Mongo mongo;

        try {
            mongo = new Mongo(new MongoURI(mongoUri));
        } catch (UnknownHostException e) {
            throw new PaddingtonException(e);
        }

        mongo.setWriteConcern(WriteConcern.SAFE);
        return mongo;
    }

    public MongoConnectionDetails createDefaultConnectionForCustomer(String customerDatabaseName) {
        Mongo mongo = customersSharedMongoTemplate.getDb().getMongo();

        return MongoConnectionDetails.createFromMongo(mongo, customerDatabaseName);
    }
}
