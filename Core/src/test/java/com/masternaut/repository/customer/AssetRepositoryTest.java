package com.masternaut.repository.customer;

import com.masternaut.domain.Asset;
import com.masternaut.repository.BaseCustomerRepositoryTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AssetRepositoryTest extends BaseCustomerRepositoryTest {
    private AssetRepository assetRepository;

    @Before
    public void before() {
        assetRepository = repositoryFactory.createRepository(AssetRepository.class);
    }

    @Test
    public void saveAndLoad() {
        Asset asset = new Asset();
        asset.setCustomerId(customer1.getId());
        asset.setName("MyAssetName");

        assetRepository.save(asset);

        Asset loadedAsset = assetRepository.findById(asset.getId(), customer1.getId());

        assertEquals(asset.getId(), loadedAsset.getId());
        assertEquals("MyAssetName", loadedAsset.getName());
        assertEquals(customer1.getId(), loadedAsset.getCustomerId());
    }

    @Test
    public void loadCaching(){
        Asset asset = new Asset();
        asset.setCustomerId(customer1.getId());
        asset.setName("MyAssetName");

        assetRepository.save(asset);

        Asset loadedAsset1 = assetRepository.findById(asset.getId(), customer1.getId());
        Asset loadedAsset2 = assetRepository.findById(asset.getId(), customer1.getId());

        assertEquals(loadedAsset1, loadedAsset2);
    }
 }
