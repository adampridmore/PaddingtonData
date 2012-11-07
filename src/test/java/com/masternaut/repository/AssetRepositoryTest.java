package com.masternaut.repository;

import com.masternaut.factory.RepositoryFactory;
import com.masternaut.domain.Asset;
import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/UnitTest-context.xml")
public class AssetRepositoryTest {
    @Autowired
    private RepositoryFactory repositoryFactory;

    private AssetRepository assetRepository;

    @Before
    public void before() {
        assetRepository = repositoryFactory.createRepository(AssetRepository.class);
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);

        customerRepository.deleteAll();

        Customer customer = new Customer();
        customer.setId("MyCustomerId");
        customer.setName("MyCustomerName");
        MongoConnectionDetails connectionDetails= new MongoConnectionDetails("UnitTest_MyCustomerName_Domain");
        customer.setDomainMongoConnectionDetails(connectionDetails);
        customerRepository.save(customer);

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
