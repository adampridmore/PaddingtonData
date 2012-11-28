package com.masternaut;

public abstract class CustomerIdentifiable extends Identifiable {
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerIdentifiable{" +
                "customerId='" + customerId + '\'' +
                "} " + super.toString();
    }
}
