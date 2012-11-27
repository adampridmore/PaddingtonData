package com.masternaut.repository.system;

import com.masternaut.domain.Customer;
import com.masternaut.repository.BaseSystemRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerRepository extends BaseSystemRepository<Customer>{
    public CustomerRepository() {
        super(Customer.class);
    }

    @Cacheable(value = "customer")
    @Override
    public Customer findById(String id) {
        return super.findById(id);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Cacheable(value = "customer")
    @Override
    public Customer tryFindById(String id) {
        return super.tryFindById(id);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public Customer tryFindByName(String customerName) {
        Criteria criteria = Criteria.where("name").is(customerName);

        Query query = new Query(criteria);

        List<Customer> customers = systemMongoTemplate.find(query, Customer.class);

        if (customers.size() == 0){
            return null;
        }

        if (customers.size() > 1){
            throw new RuntimeException(String.format("Too many customers found with name: '%s'.",customerName));
        }

        return customers.get(0);
    }
}
