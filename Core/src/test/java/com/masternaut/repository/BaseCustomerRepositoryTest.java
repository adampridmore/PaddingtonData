package com.masternaut.repository;

import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.factory.CustomerMongoFactory;
import com.masternaut.repository.system.CustomerRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/UnitTest-context.xml")
public abstract class BaseCustomerRepositoryTest {

    @Autowired
    protected CustomerMongoFactory customerMongoFactory;

    @Autowired
    protected CustomerRepository customerRepository;

    protected Customer customer1;
    protected Customer customer2;
    protected Customer customer3;

    @Before
    public void baseBefore() {
        customerRepository.deleteAll();

        String sharedCustomerDatabaseName = "SharedCustomerDatabase";

        customer1 = createAndSaveTestCustomer(1, customerRepository, sharedCustomerDatabaseName);
        customer2 = createAndSaveTestCustomer(2, customerRepository, sharedCustomerDatabaseName);
        customer3 = createAndSaveTestCustomer(3, customerRepository, "SingleCustomerDatabase");

        customerMongoFactory.clearCustomerDatabase();
    }

    private Customer createAndSaveTestCustomer(int customerId, CustomerRepository customerRepository, Object customerDatabaseName) {
        Customer customer = new Customer();
        customer.setId(String.format("MyCustomerId%d", customerId));
        customer.setName(String.format("MyCustomerName%d", customerId));

        MongoConnectionDetails connectionDetails = new MongoConnectionDetails(String.format("UnitTest_%s", customerDatabaseName));
        customer.setDomainMongoConnectionDetails(connectionDetails);

        customerRepository.save(customer);

        return customer;
    }
}
