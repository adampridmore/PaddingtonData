package com.masternaut.repository.domain;

import com.masternaut.CustomerIdentifiable;

public class MyDomain extends CustomerIdentifiable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
