package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.CustomerIdentifiable;
import com.masternaut.domain.Asset;
import com.masternaut.domain.Customer;
import com.masternaut.domain.MongoConnectionDetails;
import com.masternaut.factory.RepositoryFactory;
import com.masternaut.repository.BaseCustomerRepository;
import com.masternaut.repository.customer.AssetRepository;
import com.masternaut.repository.customer.PersonRepository;
import com.masternaut.repository.system.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("demoData")
public class DemoDataController {

    private RepositoryFactory repositoryFactory;

    @Autowired
    public DemoDataController(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    // http://localhost:8081/paddingtonData-adminWebPages/
    // http://localhost:8081/paddingtonData-adminWebPages/home
    @RequestMapping({"reset"})
    public RedirectView resetDemoData(Map<String, Object> model) {
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);

        customerRepository.deleteAll();

        Customer customer1 = createAndSaveCustomer(customerRepository, "MyCustomerName1");
        Customer customer2 = createAndSaveCustomer(customerRepository, "MyCustomerName2");

        AssetRepository assetRepository = repositoryFactory.createRepository(AssetRepository.class);

        deleteDomainData(Arrays.asList(customer1, customer2));

        createAssets(assetRepository, customer1);
        createAssets(assetRepository, customer2);

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
                domainRepository.deleteAll(customer.getId());
            }
        }
    }

    private Iterable<BaseCustomerRepository> createDomainRepositories(List<Class<? extends BaseCustomerRepository<? extends CustomerIdentifiable>>> domainRepositoryClasses) {
        ArrayList<BaseCustomerRepository> list = new ArrayList<BaseCustomerRepository>();
        for(Class<? extends BaseCustomerRepository<? extends CustomerIdentifiable>> clazz : domainRepositoryClasses){
            list.add(repositoryFactory.createRepository(clazz));
        }
        return list;
    }

    private void createAssets(AssetRepository assetRepository, Customer customer) {
        for (int i = 0; i < 10; i++) {
            Asset asset = new Asset();
            asset.setName(String.format("Asset_%d", i));
            asset.setCustomerId(customer.getId());

            assetRepository.save(asset);
        }
    }

    private Customer createAndSaveCustomer(CustomerRepository repository, String customerName) {
        Customer customer = new Customer();

        String databaseName = String.format("CustomerDomain_%s", customerName);
        customer.setDomainMongoConnectionDetails(new MongoConnectionDetails(databaseName));

        customer.setName(customerName);
        repository.save(customer);

        return customer;
    }
}
