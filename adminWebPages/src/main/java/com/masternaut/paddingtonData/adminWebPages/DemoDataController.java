package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.BulkInsertBatcher;
import com.masternaut.domain.Asset;
import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.factory.CustomerMongoFactory;
import com.masternaut.repository.customer.AssetRepository;
import com.masternaut.repository.customer.RouteResultRepository;
import com.masternaut.repository.system.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

@Controller
@RequestMapping("demoData")
public class DemoDataController {

    @Autowired
    private CustomerMongoFactory customerMongoFactory;

    @Autowired
    private CustomerRepository customerRepository ;

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
            String customerDatabaseName = "Customers_Default";

            if (i == 9){
                customerDatabaseName = "Customer_" + Integer.toString(i);
            }

            Customer customer = createAndSaveCustomer(Integer.toString(i), customerDatabaseName);

            int numberOfAssetToCreate = (i + 1) * 10;

            assetIndex += createAssets(customer, numberOfAssetToCreate, assetIndex);
        }

        return syncCustomerStatistics();
    }

    @RequestMapping({"simulateLoad"})
    public RedirectView simulateLoad(@RequestParam(value = "numberToCreate", defaultValue = "1000") int numberToCreate) {
        throw new NotImplementedException();
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

        String databaseName = String.format("Test_System_%s", customerDatabaseName);
        customer.setDomainMongoConnectionDetails(new MongoConnectionDetails(databaseName));

        customerRepository.save(customer);

        return customer;
    }
}
