package com.masternaut.repository.customer;

import com.masternaut.domain.Asset;
import com.masternaut.repository.BaseCustomerRepositoryTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AssetRepositoryTest extends BaseCustomerRepositoryTest{
    private AssetRepository assetRepository;

    @Before
    public void before() {
        assetRepository = repositoryFactory.createRepository(AssetRepository.class);
        assetRepository.deleteAll("MyCustomerId");
    }

    @Test
    public void saveAndLoad(){
        Asset asset = new Asset();
        asset.setCustomerId("MyCustomerId");
        asset.setName("MyAssetName");

        assetRepository.save(asset);

        Asset loadedAsset = assetRepository.findById(asset.getId(), "MyCustomerId");

        assertEquals(asset.getId(), loadedAsset.getId());
        assertEquals("MyAssetName", loadedAsset.getName());
        assertEquals("MyCustomerId", loadedAsset.getCustomerId());
    }
}
