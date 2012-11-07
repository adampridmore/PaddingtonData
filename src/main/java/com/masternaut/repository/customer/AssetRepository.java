package com.masternaut.repository.customer;

import com.masternaut.PaddingtonDatabase;
import com.masternaut.repository.BaseCustomerRepository;
import com.masternaut.domain.Asset;
import com.masternaut.factory.RepositoryFactory;

@PaddingtonDatabase(type = PaddingtonDatabase.DatabaseType.CustomerDomain)
public class AssetRepository extends BaseCustomerRepository<Asset> {
    public AssetRepository(RepositoryFactory repositoryFactory) {
        super(repositoryFactory, Asset.class);
    }
}
