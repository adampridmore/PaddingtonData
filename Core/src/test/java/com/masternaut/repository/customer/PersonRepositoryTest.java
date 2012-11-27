package com.masternaut.repository.customer;

import com.masternaut.domain.Person;
import com.masternaut.repository.BaseCustomerRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonRepositoryTest extends BaseCustomerRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void loadAndSave(){
        Person person = new Person();
        person.setCustomerId(customer1.getId());
        person.setName("MyPersonName");

        personRepository.save(person);

        personRepository.findById(person.getId(), customer1.getId());
    }
}
