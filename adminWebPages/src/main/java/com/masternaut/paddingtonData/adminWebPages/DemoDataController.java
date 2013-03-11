package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.BulkInsertBatcher;
import com.masternaut.domain.*;
import com.masternaut.factory.CustomerMongoFactory;
import com.masternaut.repository.customer.AssetRepository;
import com.masternaut.repository.customer.RouteResultRepository;
import com.masternaut.repository.system.CustomerRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("demoData")
public class DemoDataController {

    @Autowired
    private CustomerMongoFactory customerMongoFactory;

    @Autowired
    private CustomerRepository2 customerRepository ;

    @Autowired
    private AssetRepository assetRepository ;

    @Autowired
    private RouteResultRepository routeResultRepository ;

    private final String longString = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    @RequestMapping({"reset"})
    public RedirectView resetDemoData(Map<String, Object> model) {
        clearAllData();

        Integer assetIndex = 0;

        for (int i = 0; i < 10; i++) {
            String customerDatabaseName = null;

            if (i == 9){
                customerDatabaseName = "Test_System_Customer_" + Integer.toString(i);
            }

            Customer customer = createAndSaveCustomer(Integer.toString(i), customerDatabaseName);

            int numberOfAssetToCreate = (i + 1) * 10;

            assetIndex += createAssets(customer, numberOfAssetToCreate, assetIndex);
        }

        return syncCustomerStatistics();
    }

    @RequestMapping({"simulateLoad"})
    public RedirectView simulateLoad(@RequestParam(value = "numberToCreate", defaultValue = "1000") int numberToCreate) {
        List<Customer> allCustomers = customerRepository.findAll();
        List<Asset> allAssets = new ArrayList<Asset>();
        for(Customer customer : allCustomers){
            allAssets.addAll(assetRepository.findByCustomerId(customer.getId()));
        }

        RedirectView redirectView = new RedirectView("../customers");

        if (allAssets.size() == 0){
            return redirectView;
        }

        BulkInsertBatcher<RouteResult> bulkInsertBatcher = new BulkInsertBatcher<RouteResult>(routeResultRepository);

        Random random = new Random();
        for(int i = 0 ; i < numberToCreate ; i++){

            Asset randomAsset = getRandomAsset(allAssets, random);

            RouteResult routeResult = new RouteResult();
            routeResult.setAssetId(randomAsset.getId());
            routeResult.setCustomerId(randomAsset.getCustomerId());
            routeResult.setEventDateTime(new Date());
            routeResult.setAddress("My Address");
            routeResult.setPoint(new Point(123,456));

            bulkInsertBatcher.add(routeResult);
        }

        bulkInsertBatcher.flush();

        return redirectView;
    }

    private Asset getRandomAsset(List<Asset> allAssets, Random random) {

        int randomAssetIndex = random.nextInt(allAssets.size() - 1) + 1;

        return allAssets.get(randomAssetIndex);
    }

    @RequestMapping({"clearAllData"})
    public RedirectView clearAllData() {
        customerMongoFactory.clearCustomerDatabase();

        return new RedirectView("../customers");
    }

    @RequestMapping({"syncCustomerStatistics"})
    public RedirectView syncCustomerStatistics() {
        for(Customer customer : customerRepository.findAll()){
            long numberOfAssets = assetRepository.countForCustomerId(customer.getId());
            customer.setNumberOfAssets(numberOfAssets);

            long numberOfRouteResults = routeResultRepository.countForCustomerId(customer.getId());
            customer.setNumberOfRouteResults(numberOfRouteResults);

            customerRepository.save(customer);
        }

        return new RedirectView("../customers");
    }

    private int createAssets(Customer customer, int numberToCreate, Integer assetIndex) {
        BulkInsertBatcher<Asset> batcher = new BulkInsertBatcher<Asset>(assetRepository);

        for (int i = 0; i < numberToCreate; i++) {
            Asset asset = new Asset();
            asset.setName(String.format("Asset_%d_%s", i, longString));
            asset.setCustomerId(customer.getId());

            asset.setId(Integer.toString(assetIndex));
            assetIndex++;

            batcher.add(asset);
        }

        batcher.flush();

        return numberToCreate;
    }

    private Customer createAndSaveCustomer(String customerId, String customerDatabaseName) {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName(String.format("CustomerName_%s", customerId));

        if (customerDatabaseName != null){
            String mongoUri = customerMongoFactory.createDefaultConnectionForCustomer(customerDatabaseName);

            MongoDatabaseConnectionDetails connectionDetails = new MongoDatabaseConnectionDetails(mongoUri);

            customer.setMongoDatabaseConnectionDetails(connectionDetails);
        }

        customerRepository.save(customer);

        return customer;
    }
}
