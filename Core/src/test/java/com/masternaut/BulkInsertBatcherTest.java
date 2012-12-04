package com.masternaut;

import com.masternaut.repository.customer.MyDomainRepository;
import com.masternaut.repository.domain.MyDomain;
import com.masternaut.unitTestHelpers.BaseCustomerRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BulkInsertBatcherTest extends BaseCustomerRepositoryTest {

    @Autowired
    private MyDomainRepository myDomainRepository;

    @Test
    public void basicInsert(){
        BulkInsertBatcher<MyDomain> bulkInsertBatcher = new BulkInsertBatcher<MyDomain>(myDomainRepository);

        MyDomain t = new MyDomain();
        t.setCustomerId(customer1.getId());
        t.setName("MyName");
        bulkInsertBatcher.add(t);

        bulkInsertBatcher.flush();

        List<MyDomain> allForCustomer = myDomainRepository.findAllForCustomer(customer1.getId());

        assertEquals(1, allForCustomer.size());
        assertEquals("MyName", allForCustomer.get(0).getName());
    }

    @Test
    public void multiCustomerInsert(){
        BulkInsertBatcher<MyDomain> bulkInsertBatcher = new BulkInsertBatcher<MyDomain>(myDomainRepository);

        MyDomain myDomainC1 = new MyDomain();
        myDomainC1.setCustomerId(customer1.getId());

        MyDomain myDomainC2 = new MyDomain();
        myDomainC2.setCustomerId(customer2.getId());

        bulkInsertBatcher.add(myDomainC1);
        bulkInsertBatcher.add(myDomainC2);

        bulkInsertBatcher.flush();

        List<MyDomain> allForCustomer1 = myDomainRepository.findAllForCustomer(customer1.getId());
        assertEquals(1, allForCustomer1.size());

        List<MyDomain> allForCustomer2 = myDomainRepository.findAllForCustomer(customer2.getId());
        assertEquals(1, allForCustomer2.size());
    }
}
