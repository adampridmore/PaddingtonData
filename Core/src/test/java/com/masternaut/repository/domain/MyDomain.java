package com.masternaut.repository.domain;

import com.masternaut.CustomerIdentifiedV2;

public class MyDomain extends CustomerIdentifiedV2 {
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
