package com.masternaut.repository.customer;

import com.masternaut.repository.BaseCustomerRepository;
import com.masternaut.repository.domain.MyDomain;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyDomainRepository extends BaseCustomerRepository<MyDomain> {
    public MyDomainRepository() {
        super(MyDomain.class);
    }

    public Set<MongoOperations> createLotsOfCustomerConnections(String customerId, int numberToCreate) {
        Set<MongoOperations> set = new HashSet<MongoOperations>();

        for (int i = 0; i < numberToCreate; i++) {
            MongoOperations mongoTemplate = createMongoOperations(customerId);

            set.add(mongoTemplate);
        }

        return set;
    }

    public void eval(String customerId, String javascriptText) {
        DB mongoDB = createMongoDB(customerId);

        // This doesn't support nolock, so use command instead.
        // mongoDB.eval(javascriptText,null);

        // From:
        // http://docs.mongodb.org/manual/reference/command/eval/#eval
        DBObject command = new BasicDBObject();
        command.put("eval", javascriptText);
//        command.put("args", arrayOfArgs)
        command.put("nolock", true);

        mongoDB.command(command);
    }
}
