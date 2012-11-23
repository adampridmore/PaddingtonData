package com.masternaut.repository.customer;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.domain.RouteResult;
import com.masternaut.factory.RepositoryFactory;
import com.masternaut.repository.BaseCustomerRepository;

@PaddingtonDatabase(type = PaddingtonDatabase.DatabaseType.Customer)
public class RouteResultRepository extends BaseCustomerRepository<RouteResult>{
    public RouteResultRepository(RepositoryFactory repositoryFactory) {
        super(repositoryFactory, RouteResult.class);
    }
}
