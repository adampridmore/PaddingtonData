package com.masternaut;

public abstract class Identifiable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Identifiable{" +
                "id='" + id + '\'' +
                '}';
    }
}
