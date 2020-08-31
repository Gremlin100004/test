package com.senla.carservice.objectadjuster.propertyinjection.enumeration;

public enum TypeField {

    SHORT(Short.class),
    LONG(Long.class),
    STRING(String.class),
    BOOLEAN(Boolean.class),
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    DEFAULT(null);
    private final Class<?> referenceDataTypeClass;

    TypeField(final Class<?> referenceDataTypeClass) {
        this.referenceDataTypeClass = referenceDataTypeClass;
    }

    public Class<?> getReferenceDataTypeClass() {
        return referenceDataTypeClass;
    }
}