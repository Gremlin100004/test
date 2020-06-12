package com.senla.carservice.repository;

import com.senla.carservice.domain.Master;

public class CarService  {
    private String name;
    private Order[] orders;
    private Master[] masters;
    private Garage[] garages;

    public CarService(String name) {
        this.name = name;
        this.orders = new Order[0];
        this.masters = new Master[0];
        this.garages = new Garage[0];
    }

    public String getName() {
        return name;
    }


    public Order[] getOrders() {
        return this.orders;
    }

    public Master[] getMasters() {
        return this.masters;
    }

    public Garage[] getGarages() {
        return this.garages;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setOrders(Order[] orders) {
        this.orders = orders;
    }


    public void setMasters(Master[] masters) {
        this.masters = masters;
    }


    public void setGarages(Garage[] garages) {
        this.garages = garages;
    }
}