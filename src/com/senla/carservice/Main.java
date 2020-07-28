package com.senla.carservice;

import com.senla.carservice.container.Container;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        CarOfficeController carOfficeController = Container.getObject(CarOfficeController.class);
        carOfficeController.deserializeEntities();
        MenuController menuController = Container.getObject(MenuController.class);

        menuController.run();
        carOfficeController.serializeEntities();
    }
}