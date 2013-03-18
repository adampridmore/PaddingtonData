package com.masternaut.unitTestHelpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

public class LocalConnectionUriHelperFactory {

    @Value("${unittest_default_local_mongouri}")
    private String unittest_default_local_mongouri;

    public String createDefaultLocalConnection(String databaseName) {
        Assert.hasText(databaseName);

        return String.format("%s/%s", unittest_default_local_mongouri, databaseName);
    }
}
