package org.example.config;

import org.example.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.example.constant.Constant.SOLUTION_CONFIG;

public class ConfigReader {

    public Map<String, String> getConfigMap() {
        try {
            var properties = loadProperties();
            Map<String, String> configMap = new HashMap<>(properties.size());
            for (Map.Entry<Object, Object> item : properties.entrySet()) {
                configMap.put((String) item.getKey(), (String) item.getValue());
            }
            return configMap;
        } catch (IOException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    private Properties loadProperties() throws IOException {
        var properties = new Properties();
        var inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(SOLUTION_CONFIG);
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }
}