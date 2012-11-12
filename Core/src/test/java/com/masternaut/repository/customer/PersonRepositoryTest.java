package com.masternaut.repository.customer;

import com.masternaut.domain.Person;
import com.masternaut.repository.BaseCustomerRepositoryTest;
import org.junit.Before;
import org.junit.Test;

public class PersonRepositoryTest extends BaseCustomerRepositoryTest {
    private PersonRepository personRepository;

    @Before
    public void before() {
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
