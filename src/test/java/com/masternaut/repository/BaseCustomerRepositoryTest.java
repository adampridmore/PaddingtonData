package com.masternaut.repository;

import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.factory.RepositoryFactory;
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
    protected RepositoryFactory repositoryFactory;

    protected final String testCustomerId = "MyCustomerId";

    @Before
    public void baseBefore(){
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);
        customerRepository.deleteAll();

        Customer customer = new Customer();
        customer.setId(testCustomerId);
        customer.setName("MyCustomerName");

        MongoConnectionDetails connectionDetails= new MongoConnectionDetails("UnitTest_MyCustomerName_Domain");
        customer.setDomainMongoConnectionDetails(connectionDetails);

        customerRepository.save(customer);
    }
}
