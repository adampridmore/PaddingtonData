package com.masternaut.repository.customer;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.domain.Person;
import com.masternaut.repository.BaseCustomerRepository;
import org.springframework.stereotype.Component;

@PaddingtonDatabase(type = PaddingtonDatabase.DatabaseType.Customer)
@Component
public class PersonRepository extends BaseCustomerRepository<Person>{
    public PersonRepository() {
        super(Person.class);
    }
}
