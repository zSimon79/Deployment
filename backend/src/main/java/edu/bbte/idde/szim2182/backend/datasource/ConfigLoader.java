package edu.bbte.idde.szim2182.backend.datasource;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {
    public static ApplicationConfig loadConfig(String profile) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream configStream = classLoader.getResourceAsStream("config-" + profile + ".json");
        if (configStream == null) {
            throw new IOException("Configuration file not found for profile: " + profile);
        }
        return new ObjectMapper().readValue(configStream, ApplicationConfig.class);
    }
}