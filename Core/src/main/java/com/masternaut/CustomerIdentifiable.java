package com.masternaut;

import org.springframework.data.annotation.Transient;

public abstract class CustomerIdentifiable extends Identifiable {
    @Transient
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
