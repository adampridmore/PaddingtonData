package com.masternaut.repository;

import com.masternaut.BaseCustomerRepository;
import com.masternaut.domain.Asset;
import com.masternaut.factory.RepositoryFactory;

public class AssetRepository extends BaseCustomerRepository<Asset> {
    public AssetRepository(RepositoryFactory repositoryFactory) {
        super(repositoryFactory, Asset.class);
    }
}
