package com.masternaut.repository.customer;

import com.masternaut.domain.Asset;
import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.repository.BaseCustomerRepositoryTest;
import com.masternaut.repository.system.CustomerRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void findAllForCustomerId(){

        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);
        Customer otherCustomer = new Customer();
        otherCustomer.setName("OtherCustomer");
        otherCustomer.setDomainMongoConnectionDetails(new MongoConnectionDetails("UnitTest_MyOtherCustomerName_Domain"));
        customerRepository.save(otherCustomer);

        Asset customerAsset = new Asset();
        customerAsset.setCustomerId(testCustomerId);
        customerAsset.setName("MyCustomerAssetName");
        assetRepository.save(customerAsset);

        Asset otherCustomerAsset = new Asset();
        otherCustomerAsset.setCustomerId(testCustomerId);
        otherCustomerAsset.setName("MyOtherCustomerAsset");
        otherCustomerAsset.setCustomerId(otherCustomer.getId());
        assetRepository.save(otherCustomerAsset);

        List<Asset> foundAssets = assetRepository.findAllForCustomer(testCustomerId);

        assertEquals(1, foundAssets.size());
        assertEquals("MyCustomerAssetName", foundAssets.get(0).getName());
        assertEquals(testCustomerId, foundAssets.get(0).getCustomerId());
    }
}
