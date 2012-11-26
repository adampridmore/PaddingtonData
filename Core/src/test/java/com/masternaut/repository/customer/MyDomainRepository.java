package com.masternaut.repository.customer;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.repository.BaseCustomerRepository;
import com.masternaut.repository.domain.MyDomain;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@PaddingtonDatabase(type = PaddingtonDatabase.DatabaseType.Customer)
@Component
public class MyDomainRepository extends BaseCustomerRepository<MyDomain> {
    public MyDomainRepository() {
        super( MyDomain.class);
    }

    public Set<MongoTemplate> createLotsOfCustomerConnections(String customerId, int numberToCreate) {
        Set<MongoTemplate> set = new HashSet<MongoTemplate>();

        for(int i = 0 ; i < numberToCreate ; i++){
            MongoTemplate mongoTemplate = repositoryFactory.createMongoTemplateForCustomerId(customerId);

            set.add(mongoTemplate);
        }

        return set;
    }
}
