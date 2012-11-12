package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.domain.Customer;
import com.masternaut.factory.RepositoryFactory;
import com.masternaut.repository.system.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class DemoDataController {

    private RepositoryFactory repositoryFactory;

    @Autowired
    public DemoDataController(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    // http://localhost:8081/paddingtonData-adminWebPages/
    // http://localhost:8081/paddingtonData-adminWebPages/home
    @RequestMapping({"demoData/reset"})
    public RedirectView resetDemoData(Map<String, Object> model) {
        CustomerRepository repository = repositoryFactory.createRepository(CustomerRepository.class);

        repository.deleteAll();

        createAndSaveCustomer(repository, "My Customer Name 1");
        createAndSaveCustomer(repository, "My Customer Name 2");

        return new RedirectView("../home");
    }

    private void createAndSaveCustomer(CustomerRepository repository, String customerName) {
        Customer customer = new Customer();
        customer.setName(customerName);
        repository.save(customer);
    }
}
