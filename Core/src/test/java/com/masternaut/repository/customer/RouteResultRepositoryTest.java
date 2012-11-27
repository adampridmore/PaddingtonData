package com.masternaut.repository.customer;

import com.masternaut.domain.Point;
import com.masternaut.domain.RouteResult;
import com.masternaut.repository.BaseCustomerRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RouteResultRepositoryTest extends BaseCustomerRepositoryTest {

    @Autowired
    private RouteResultRepository routeResultRepository;

    @Test
    public void saveAndLoad(){

        RouteResult routeResult = new RouteResult();
        routeResult.setAddress("MyAddress");
        routeResult.setAssetId("MyAssetId");
        routeResult.setPoint(new Point(123,456));
        routeResult.setCustomerId(customer1.getId());
        routeResult.setEventDateTime(new Date());

        routeResultRepository.save(routeResult);

        RouteResult loadedRouteResult = routeResultRepository.findById(routeResult.getId(), customer1.getId());
        assertEquals("MyAddress", loadedRouteResult.getAddress());
        assertEquals("MyAssetId", loadedRouteResult.getAssetId());
        assertEquals(123, loadedRouteResult.getPoint().getLatitude(),0);
        assertEquals(456, loadedRouteResult.getPoint().getLongitude(),0);
        assertEquals(routeResult.getEventDateTime(), loadedRouteResult.getEventDateTime());
    }
}

