package com.masternaut.factory;

import com.masternaut.domain.Asset;
import com.masternaut.domain.RouteResult;
import com.masternaut.unitTestHelpers.BaseCustomerRepositoryTest;
import com.masternaut.repository.customer.AssetRepository;
import com.masternaut.repository.customer.RouteResultRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.mongodb.util.MyAsserts.assertEquals;

public class ClearAllCustomerDataTest extends BaseCustomerRepositoryTest{

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private RouteResultRepository routeResultRepository;

    @Test
    public void clearAllCustomersDatabase(){
        Asset asset1 = new Asset();
        asset1.setCustomerId(customer1.getId());

        Asset asset2 = new Asset();
        asset2.setCustomerId(customer2.getId());

        Asset asset3 = new Asset();
        asset3.setCustomerId(customer3.getId());

        assetRepository.save(Arrays.asList(asset1,  asset2, asset3));

        RouteResult routeResult = new RouteResult();
        routeResult.setCustomerId(customer1.getId());
        routeResultRepository.save(routeResult);

        customerMongoFactory.clearCustomerDatabase();

        assertEquals(0, assetRepository.countForAllCustomers());
        assertEquals(0, routeResultRepository.countForAllCustomers());
    }
}
