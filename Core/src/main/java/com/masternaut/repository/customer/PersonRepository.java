package com.masternaut.repository.customer;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.repository.BaseCustomerRepository;
import com.masternaut.domain.Person;
import com.masternaut.factory.RepositoryFactory;

@PaddingtonDatabase(type = PaddingtonDatabase.DatabaseType.Customer)
public class PersonRepository extends BaseCustomerRepository<Person>{
    public PersonRepository(RepositoryFactory repositoryFactory) {
        super(repositoryFactory, Person.class);
    }
}
