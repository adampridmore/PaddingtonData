package com.masternaut.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {
    private ApplicationContext applicationContext;

    @Autowired
    public RepositoryFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public <T> T createRepository(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
