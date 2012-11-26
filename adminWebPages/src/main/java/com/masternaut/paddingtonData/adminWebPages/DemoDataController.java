package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.BulkInsertBatcher;
import com.masternaut.CustomerIdentifiable;
import com.masternaut.domain.Asset;
import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.factory.RepositoryFactory;
import com.masternaut.repository.BaseCustomerRepository;
import com.masternaut.repository.customer.AssetRepository;
import com.masternaut.repository.customer.PersonRepository;
import com.masternaut.repository.customer.RouteResultRepository;
import com.masternaut.repository.system.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("demoData")
public class DemoDataController {

    @Autowired
    private RepositoryFactory repositoryFactory;

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
        repositoryFactory.clearCustomerDatabase();

        return new RedirectView("../customers");
    }

    @RequestMapping({"syncCustomerStatistics"})
    public RedirectView syncCustomerStatistics() {
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);
        AssetRepository assetRepository = repositoryFactory.createRepository(AssetRepository.class);
        RouteResultRepository routeResultRepository = repositoryFactory.createRepository(RouteResultRepository.class);

        for(Customer customer : customerRepository.findAll()){
            long numberOfAssets = assetRepository.countForCustomerId(customer.getId());
            customer.setNumberOfAssets(numberOfAssets);

            long numberOfRouteResults = routeResultRepository.countForCustomerId(customer.getId());
            customer.setNumberOfRouteResults(numberOfRouteResults);

            customerRepository.save(customer);
        }

        return new RedirectView("../customers");
    }

    private void deleteDomainData(Iterable<Customer> customers) {
        List<Class<? extends BaseCustomerRepository<? extends CustomerIdentifiable>>> domainRepositoryClasses
                = Arrays.asList(
                AssetRepository.class,
                PersonRepository.class);

        Iterable<BaseCustomerRepository> domainRepositories = createDomainRepositories(domainRepositoryClasses);

        for (BaseCustomerRepository domainRepository : domainRepositories) {
            for (Customer customer : customers) {
                domainRepository.deleteAllForCustomer(customer.getId());
            }
        }
    }

    private Iterable<BaseCustomerRepository> createDomainRepositories(List<Class<? extends BaseCustomerRepository<? extends CustomerIdentifiable>>> domainRepositoryClasses) {
        ArrayList<BaseCustomerRepository> list = new ArrayList<BaseCustomerRepository>();
        for (Class<? extends BaseCustomerRepository<? extends CustomerIdentifiable>> clazz : domainRepositoryClasses) {
            list.add(repositoryFactory.createRepository(clazz));
        }
        return list;
    }

    private int createAssets(Customer customer, int numberToCreate, Integer assetIndex) {

        AssetRepository assetRepository = repositoryFactory.createRepository(AssetRepository.class);

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

        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);

        customerRepository.save(customer);

        return customer;
    }
}
