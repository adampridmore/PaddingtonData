package com.masternaut.repository;

import com.masternaut.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/UnitTest-context.xml")
public class CustomerRepositoryTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void before(){
        mongoTemplate.dropCollection(Customer.class);
    }

    @Test
    public void smokeTest() {
        assertNotNull(context);
    }

    @Test
    public void saveAndLoad() {
        CustomerRepository customerRepository = new CustomerRepository(mongoTemplate);

        Customer customer = new Customer();
        customer.setName("MyCustomerName");

        customerRepository.save(customer);

        Customer loadedCustomer = customerRepository.findById(customer.getId());

        assertEquals(customer.getId(), loadedCustomer.getId());
        assertEquals("MyCustomerName", loadedCustomer.getName());
    }

    @Test
    public void countRecords() {
        CustomerRepository customerRepository = new CustomerRepository(mongoTemplate);

        Customer customer = new Customer();
        customer.setName("MyCustomerName");

        customerRepository.save(customer);

        assertEquals(1, customerRepository.count());
    }
}

