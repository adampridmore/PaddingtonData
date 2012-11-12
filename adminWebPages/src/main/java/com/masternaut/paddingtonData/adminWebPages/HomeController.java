package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.domain.Customer;
import com.masternaut.factory.RepositoryFactory;
import com.masternaut.repository.system.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private RepositoryFactory repositoryFactory;

    @Autowired
    public HomeController(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    // http://localhost:8081/paddingtonData-adminWebPages/
    // http://localhost:8081/paddingtonData-adminWebPages/home
    @RequestMapping({"/", "/home"})
    public String showHomePage(Map<String, Object> model){
        CustomerRepository repository = repositoryFactory.createRepository(CustomerRepository.class);

        List<Customer> customerList = repository.findAll();

        model.put("customers", customerList);

        return "home";
    }
}
