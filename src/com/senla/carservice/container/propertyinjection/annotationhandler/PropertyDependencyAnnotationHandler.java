package com.senla.carservice.container.propertyinjection.annotationhandler;

import com.senla.carservice.container.property.PropertyLoader;
import com.senla.carservice.container.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.container.propertyinjection.enumaration.DefaultValue;
import com.senla.carservice.container.propertyinjection.enumaration.TypeField;
import com.senla.carservice.exception.BusinessException;

import java.lang.reflect.Field;

public class PropertyDependencyAnnotationHandler {
    private static final String POINT = ".";

    public void configure(Object inputObject) {
        Class<?> implementClass = inputObject.getClass();
        for (Field field : implementClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigProperty.class)) {
                continue;
            }
            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
            String propertyFileName = getPropertyFileName(annotation);
            String propertyName = getPropertyName(
                annotation, inputObject.getClass().getName() + POINT + field.getName());
            Class<?> fieldType = getFieldType(annotation, field.getType());
            String value = PropertyLoader.getPropertyValue(propertyFileName, propertyName);
            field.setAccessible(true);
            injectValueInField(field, value, fieldType, inputObject);
            field.setAccessible(true);
        }
    }

    private String getPropertyFileName(ConfigProperty annotation) {
        if (annotation.configName().isEmpty()) {
            return DefaultValue.PROPERTY_FILE_NAME.toString();
        } else {
            return annotation.propertyName();
        }
    }

    private String getPropertyName(ConfigProperty annotation, String defaultPropertyName) {
        if (annotation.propertyName().isEmpty()) {
            return defaultPropertyName;
        } else {
            return annotation.propertyName();
        }
    }

    private Class<?> getFieldType(ConfigProperty annotation, Class<?> defaultType) {
        if (annotation.type().equals(TypeField.DEFAULT)) {
            return defaultType;
        } else {
            return annotation.type().getaClass();
        }
    }

    private void injectValueInField(Field field, String value, Class<?> fieldType, Object inputObject) {
        try {
            if (fieldType.equals(Boolean.class)) {
                field.set(inputObject, Boolean.parseBoolean(value));
            } else if (fieldType.equals(Short.class)) {
                field.set(inputObject, Short.parseShort(value));
            } else if (fieldType.equals(Integer.class)) {
                field.set(inputObject, Integer.parseInt(value));
            } else if (fieldType.equals(Long.class)) {
                field.set(inputObject, Long.parseLong(value));
            } else if (fieldType.equals(Double.class)) {
                field.set(inputObject, Double.parseDouble(value));
            } else if (fieldType.equals(String.class)) {
                field.set(inputObject, value);
            }
        } catch (IllegalAccessException e) {
            throw new BusinessException("Error set value to a field");
        } catch (NumberFormatException e) {
            throw new BusinessException("Error parse string value");
        }
    }
}