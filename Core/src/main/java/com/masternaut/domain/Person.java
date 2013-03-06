package com.masternaut.domain;

import com.masternaut.CustomerIdentifiedV2;

public class Person extends CustomerIdentifiedV2 {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
