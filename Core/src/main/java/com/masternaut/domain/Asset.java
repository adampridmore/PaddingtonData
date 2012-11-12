package com.masternaut.domain;

import com.masternaut.CustomerIdentifiable;

public class Asset extends CustomerIdentifiable{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
