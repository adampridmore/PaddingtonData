package com.masternaut.domain;

import com.masternaut.Identifiable;

public class SystemSettings extends Identifiable {
    private String systemName;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
