package com.masternaut.unitTestHelpers;

import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoDatabaseConnectionDetails;
import com.masternaut.factory.CustomerMongoFactory;
import com.masternaut.repository.system.CustomerRepository2;
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
    protected CustomerRepository2 customerRepository;

    @Autowired
    private LocalConnectionUriHelperFactory localConnectionUriHelperFactory;

    protected Customer customer1;
    protected Customer customer2;
    protected Customer customer3;

    @Before
    public void baseBefore() {
        customerRepository.deleteAll();

        customer1 = createAndSaveTestCustomer(1, customerRepository, null);
        customer2 = createAndSaveTestCustomer(2, customerRepository, null);
        customer3 = createAndSaveTestCustomer(3, customerRepository, "unittest_Customer_3");

        customerMongoFactory.clearCustomerDatabase();
    }

    private Customer createAndSaveTestCustomer(int customerId, CustomerRepository2 customerRepository, String customerDatabaseName) {
        Customer customer = new Customer();
        customer.setId(String.format("MyCustomerId%d", customerId));
        customer.setName(String.format("MyCustomerName%d", customerId));

        if (customerDatabaseName != null) {
            String mongoUri = localConnectionUriHelperFactory
                    .createDefaultLocalConnection(customerDatabaseName);

            MongoDatabaseConnectionDetails connectionDetails = new MongoDatabaseConnectionDetails(mongoUri);
            customer.setMongoDatabaseConnectionDetails(connectionDetails);
        }

        customerRepository.save(customer);

        return customer;
    }
}
