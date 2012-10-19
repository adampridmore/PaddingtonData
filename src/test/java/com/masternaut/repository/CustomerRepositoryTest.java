package com.masternaut.repository;

import com.masternaut.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/UnitTest-context.xml")
public class CustomerRepositoryTest {

    @Autowired
    private RepositoryFactory repositoryFactory;

    @Test
    public void saveAndLoad() {
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);

        Customer customer = new Customer();
        customer.setName("MyCustomerName");

        customerRepository.save(customer);

        Customer loadedCustomer = customerRepository.findById(customer.getId());

        assertEquals(customer.getId(), loadedCustomer.getId());
        assertEquals("MyCustomerName", loadedCustomer.getName());
    }

    @Test
    public void countRecords() {
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);

        customerRepository.deleteAll();

        Customer customer = new Customer();
        customer.setName("MyCustomerName");

        customerRepository.save(customer);

        assertEquals(1, customerRepository.count());
    }
}