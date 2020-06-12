package com.senla.carservice.domain;

public class Car  {
    private String automaker;
    private String model;
    private String registrationNumber;

    public Car(String automaker, String model, String registrationNumber) {
        this.automaker = automaker;
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "Car{" +
                "automaker='" + automaker + '\'' +
                ", model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }

    public String getAutomaker() {
        return automaker;
    }


    public String getModel() {
        return model;
    }


    public String getRegistrationNumber() {
        return registrationNumber;
    }
}