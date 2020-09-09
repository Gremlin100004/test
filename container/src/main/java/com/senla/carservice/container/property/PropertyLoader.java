package com.senla.carservice.container.property;

import com.senla.carservice.container.exception.InitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyLoader.class);

    private PropertyLoader() {
    }

    public static String getPropertyValue(String propertyFileName, String propertyName) {
        LOGGER.debug("Method getPropertyValue");
        LOGGER.trace("Parameter propertyFileName: {}", propertyFileName);
        LOGGER.trace("Parameter propertyName: {}", propertyName);
        ClassLoader classLoader = PropertyLoader.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(propertyFileName)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new InitializationException("load resource problem");
        }
    }
}