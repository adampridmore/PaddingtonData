package com.masternaut;

public abstract class CustomerIdentifiedV2 extends Identifiable {
    //@Field("CID")
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerIdentifiedV2{" +
                "customerId='" + customerId + '\'' +
                "} " + super.toString();
    }
}
