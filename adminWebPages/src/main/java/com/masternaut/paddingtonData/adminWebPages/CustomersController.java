package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.domain.Asset;
import com.masternaut.domain.Customer;
import com.masternaut.factory.RepositoryFactory;
import com.masternaut.repository.customer.AssetRepository;
import com.masternaut.repository.system.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("customers")
public class CustomersController {
    @Autowired
    private RepositoryFactory repositoryFactory;

    @RequestMapping({"", "index"})
    public String index(Map<String, Object> model) {
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);

        List<Customer> customerList = customerRepository.findAll();

        model.put("customers", customerList);

        return "customers/index";
    }

    @RequestMapping({"details/{customerId}"})
    public String details(@PathVariable String customerId,
                          Map<String, Object> model) {
        CustomerRepository customerRepository = repositoryFactory.createRepository(CustomerRepository.class);

        Customer customer = customerRepository.findById(customerId);

        AssetRepository assetRepository = repositoryFactory.createRepository(AssetRepository.class);
        List<Asset> assetsForCustomer = assetRepository.findAllForCustomer(customerId);

        model.put("customer", customer);
        model.put("assets", assetsForCustomer);

        return "customers/details";
    }
}
