package com.masternaut.repository;

import com.masternaut.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerRepositoryTest {
    @Test
    public void saveAndLoad(){
        CustomerRepository customerRepository = new CustomerRepository();

        Customer customer = new Customer();
        customer.setName("MyCustomerName");

        customerRepository.save(customer);

        Customer loadedCustomer = customerRepository.findById(customer.getId());

        assertEquals(customer.getId(), loadedCustomer.getId());
        assertEquals("MyCustomerName", loadedCustomer.getName());
    }
}

