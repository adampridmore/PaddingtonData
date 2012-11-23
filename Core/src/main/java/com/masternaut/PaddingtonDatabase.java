package com.masternaut;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PaddingtonDatabase {
    public enum DatabaseType{System, Customer};

    public DatabaseType type();
}
