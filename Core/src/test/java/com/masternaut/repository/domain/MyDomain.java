package com.masternaut.repository.domain;

import com.masternaut.CustomerIdentifiable;

public class MyDomain extends CustomerIdentifiable {
    private String name;

    public MyDomain(String name, String customerId) {
        this.name = name;
        this.setCustomerId(customerId);
    }

    public MyDomain() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyDomain{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
