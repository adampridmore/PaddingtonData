package com.masternaut.repository.customer;

import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.domain.Person;
import com.masternaut.factory.RepositoryFactory;
import com.masternaut.repository.customer.PersonRepository;
import com.masternaut.repository.system.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/UnitTest-context.xml")
public class PersonRepositoryTest {

    @Autowired
    private RepositoryFactory repositoryFactory;

    private PersonRepository personRepository;

    @Before
    public void before() {
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);
        customerRepository.deleteAll();

        Customer customer = new Customer();
        customer.setId("MyCustomerId");
        customer.setName("MyCustomerName");
        MongoConnectionDetails connectionDetails= new MongoConnectionDetails("UnitTest_MyCustomerName_Domain");
        customer.setDomainMongoConnectionDetails(connectionDetails);
        customerRepository.save(customer);

        personRepository = repositoryFactory.createRepository(PersonRepository.class);
        personRepository.deleteAll("MyCustomerId");
    }

    @Test
    public void loadAndSave(){
        Person person = new Person();
        person.setCustomerId("MyCustomerId");
        person.setName("MyPersonName");

        personRepository.save(person);

        personRepository.findById(person.getId(), "MyCustomerId");
    }
}
