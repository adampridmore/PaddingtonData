package com.masternaut.repository.customer;

import com.masternaut.PaddingtonException;
import com.masternaut.repository.BaseCustomerRepositoryTest;
import com.masternaut.repository.domain.MyDomain;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyDomainRepositoryTest extends BaseCustomerRepositoryTest {
    private MyDomainRepository myDomainRepository;

    @Before
    public void before() {
        myDomainRepository = repositoryFactory.createRepository(MyDomainRepository.class);

        myDomainRepository.deleteAllForCustomer(customer1.getId());
        myDomainRepository.deleteAllForCustomer(customer2.getId());
        myDomainRepository.deleteAllForCustomer(customer3.getId());
    }

    @Test
    public void save_with_no_customerId() {
        MyDomain myDomain = new MyDomain();
        try {
            myDomainRepository.save(myDomain);
            fail("Exception not thrown");
        } catch (PaddingtonException pe) {
            assertEquals("CustomerId not set on MyDomain", pe.getMessage());
        }
    }

    @Test
    public void saveList() {
        MyDomain myDomain1 = new MyDomain();
        myDomain1.setCustomerId(customer1.getId());
        myDomain1.setName("A1");

        MyDomain myDomain2 = new MyDomain();
        myDomain2.setCustomerId(customer1.getId());
        myDomain2.setName("A2");

        myDomainRepository.save(Arrays.asList(myDomain1, myDomain2));

        myDomainRepository.findById(myDomain1.getId(), customer1.getId());
        myDomainRepository.findById(myDomain1.getId(), customer1.getId());
    }

    @Test
    public void deleteAllForCustomerId() {
        MyDomain asset1 = new MyDomain();
        asset1.setCustomerId(customer1.getId());
        myDomainRepository.save(asset1);

        MyDomain asset2 = new MyDomain();
        asset2.setCustomerId(customer2.getId());
        myDomainRepository.save(asset2);

        myDomainRepository.deleteAllForCustomer(customer1.getId());

        List<MyDomain> assetsForCustomer1 = myDomainRepository.findAllForCustomer(customer1.getId());
        List<MyDomain> assetsForCustomer2 = myDomainRepository.findAllForCustomer(customer2.getId());

        assertEquals(0, assetsForCustomer1.size());
        assertEquals(1, assetsForCustomer2.size());
    }

    @Test
    public void findAllForCustomerId() {
        MyDomain customerAsset = new MyDomain();
        customerAsset.setCustomerId(customer1.getId());
        customerAsset.setName("MyCustomerAssetName");
        myDomainRepository.save(customerAsset);

        MyDomain otherCustomerAsset = new MyDomain();
        otherCustomerAsset.setCustomerId(customer1.getId());
        otherCustomerAsset.setName("MyOtherCustomerAsset");
        otherCustomerAsset.setCustomerId(customer2.getId());
        myDomainRepository.save(otherCustomerAsset);

        List<MyDomain> foundAssets = myDomainRepository.findAllForCustomer(customer1.getId());

        assertEquals(1, foundAssets.size());
        assertEquals("MyCustomerAssetName", foundAssets.get(0).getName());
        assertEquals(customer1.getId(), foundAssets.get(0).getCustomerId());
    }

    @Test
    public void count() {
        MyDomain a1 = new MyDomain();
        a1.setCustomerId(customer1.getId());

        MyDomain a2 = new MyDomain();
        a2.setCustomerId(customer2.getId());

        myDomainRepository.save(a1);
        myDomainRepository.save(a2);

        assertEquals(2, myDomainRepository.countForAllCustomers());
    }

    @Test
    public void findByInvalidId() {
        try {
            myDomainRepository.findById("InvalidId", customer1.getId());
            fail("exception not thrown");
        } catch (PaddingtonException pe) {
            assertEquals("MyDomain with id of 'InvalidId' not found.", pe.getMessage());
        }
    }

    @Test
    public void countForCustomer() {
        MyDomain asset1 = new MyDomain();
        asset1.setCustomerId(customer1.getId());

        MyDomain asset2 = new MyDomain();
        asset2.setCustomerId(customer1.getId());

        MyDomain asset3 = new MyDomain();
        asset3.setCustomerId(customer2.getId());

        myDomainRepository.save(Arrays.asList(asset1, asset2, asset3));
    }

    @Test
    public void bulkInsert() {
        MyDomain asset1 = new MyDomain();
        asset1.setCustomerId(customer1.getId());

        MyDomain asset2 = new MyDomain();
        asset2.setCustomerId(customer1.getId());

        myDomainRepository.bulkInsert(Arrays.asList(asset1, asset2));

        List<MyDomain> allForCustomer = myDomainRepository.findAllForCustomer(customer1.getId());
        assertEquals(2, allForCustomer.size());
    }

    @Test
    public void customer_MongoTemplate_are_cached(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Set<MongoTemplate> lotsOfCustomerConnections = myDomainRepository.createLotsOfCustomerConnections(customer1.getId(), 10);

        stopWatch.stop();

        System.out.println(String.format("Took %fs", stopWatch.getTotalTimeSeconds()));

        assertEquals(1, lotsOfCustomerConnections.size());
        assertTrue(stopWatch.getTotalTimeSeconds() < 1);
    }
}
