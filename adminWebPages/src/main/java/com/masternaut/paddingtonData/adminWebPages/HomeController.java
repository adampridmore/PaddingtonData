package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.domain.Customer;
import com.masternaut.factory.RepositoryFactory;
import com.masternaut.repository.system.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private RepositoryFactory repositoryFactory;

    @Autowired
    public HomeController(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @RequestMapping({"/", "/home"})
    public String showHomePage(){
        CustomerRepository repository = repositoryFactory.createRepository(CustomerRepository.class);

        List<Customer> customerList = repository.findAll();

        return "home";
    }
}
