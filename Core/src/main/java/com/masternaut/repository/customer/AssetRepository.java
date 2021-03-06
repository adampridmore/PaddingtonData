package com.masternaut.repository.customer;

import com.masternaut.domain.Asset;
import com.masternaut.repository.BaseCustomerRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class AssetRepository extends BaseCustomerRepository<Asset> {
    public AssetRepository() {
        super(Asset.class);
    }

    @Cacheable(value = "asset")
    @Override
    public Asset findById(String id, String customerId) {
        return super.findById(id, customerId);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
