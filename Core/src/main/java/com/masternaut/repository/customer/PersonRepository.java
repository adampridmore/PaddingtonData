package com.masternaut.repository.customer;

import com.masternaut.domain.Person;
import com.masternaut.repository.BaseCustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class PersonRepository extends BaseCustomerRepository<Person>{
    public PersonRepository() {
        super(Person.class);
    }
}
