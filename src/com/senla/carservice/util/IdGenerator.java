package com.senla.carservice.util;

public class IdGenerator {
    private Long id;

    public IdGenerator() {
        this.id = 0L;
    }

    public Long getId() {
        return this.id++;
    }
}