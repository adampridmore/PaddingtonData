package com.masternaut.repository.customer;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.domain.RouteResult;
import com.masternaut.repository.BaseCustomerRepository;
import org.springframework.stereotype.Component;

@PaddingtonDatabase(type = PaddingtonDatabase.DatabaseType.Customer)
@Component
public class RouteResultRepository extends BaseCustomerRepository<RouteResult>{
    public RouteResultRepository() {
        super(RouteResult.class);
    }
}
