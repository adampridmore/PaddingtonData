package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.factory.RepositoryFactory;
import com.mongodb.Mongo;
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
    @RequestMapping({"/", "/home"})
    public String showHomePage(Map<String, Object> model){

        List<String> systemProperties = repositoryFactory.getDatabaseConnectionInformation();

        model.put("systemProperties", systemProperties);

        return "home";
    }
}
