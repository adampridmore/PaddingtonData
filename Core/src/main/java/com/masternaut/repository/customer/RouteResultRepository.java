package com.masternaut.repository.customer;

import com.masternaut.domain.RouteResult;
import com.masternaut.repository.BaseCustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class RouteResultRepository extends BaseCustomerRepository<RouteResult>{
    public RouteResultRepository() {
        super(RouteResult.class);
    }
}
