package org.example.config;

import org.example.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {
    public static final String SOLUTION_CONFIG = "application.yaml";

    public Map<String, String> getConfigMap() {
        Properties properties = null;
        try {
            properties = loadProperties(SOLUTION_CONFIG);
        } catch (IOException e) {
            throw new ResourceNotFoundException(e);
        }
        return Map.of(
                "driver", (String) properties.get("driver"),
                "url", (String) properties.get("url"),
                "username", (String) properties.get("username"),
                "password", (String) properties.get("password")
        );
    }

    private Properties loadProperties(String propertyFile) throws IOException {
        var properties = new Properties();
        var inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(propertyFile);
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }
}