package com.masternaut.paddingtonData.adminWebPages;

import com.masternaut.factory.CustomerMongoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private CustomerMongoFactory customerMongoFactory;

    // http://localhost:8081/paddingtonData-adminWebPages/
    @RequestMapping({"/", "/home"})
    public String showHomePage(Map<String, Object> model){

        List<String> systemProperties = customerMongoFactory.getDatabaseConnectionInformation();

        model.put("systemProperties", systemProperties);

        return "home";
    }
}
