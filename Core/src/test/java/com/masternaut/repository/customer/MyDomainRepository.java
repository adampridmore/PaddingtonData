package com.masternaut.repository.customer;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.repository.BaseCustomerRepository;
import com.masternaut.repository.domain.MyDomain;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@PaddingtonDatabase(type = PaddingtonDatabase.DatabaseType.Customer)
@Component
public class MyDomainRepository extends BaseCustomerRepository<MyDomain> {
    public MyDomainRepository() {
        super( MyDomain.class);
    }

    public Set<MongoOperations> createLotsOfCustomerConnections(String customerId, int numberToCreate) {
        Set<MongoOperations> set = new HashSet<MongoOperations>();

        for(int i = 0 ; i < numberToCreate ; i++){
            MongoOperations mongoTemplate = customerMongoFactory.create(customerId);

            set.add(mongoTemplate);
        }

        return set;
    }
}
