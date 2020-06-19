package com.senla.carservice.service;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Order;
import com.senla.carservice.repository.CarOfficeRepository;
import com.senla.carservice.repository.CarOfficeRepositoryImpl;

import java.util.ArrayList;

// все ошибки этого класса найдешь в других сервисах тоже
public final class CarOfficeServiceImpl implements CarOfficeService {
    // испольуем тип интерфейса
    private static CarOfficeServiceImpl instance;
    private final CarOfficeRepository carOfficeRepository;

    // это не синглтон с публичным конструктором
    public CarOfficeServiceImpl() {
        this.carOfficeRepository = CarOfficeRepositoryImpl.getInstance();
    }

    // испольуем тип интерфейса
    public static CarOfficeServiceImpl getInstance() {
        if (instance == null) {
            instance = new CarOfficeServiceImpl();
        }
        return instance;
    }


    @Override
    public int getNumberFreePlaceDate(ArrayList<Order> orders) {
        int numberGeneralPlace = 0;
        int numberPlaceOrders = orders.size();
        for (Garage garage : this.carOfficeRepository.getGarages())
            numberGeneralPlace += garage.getPlaces().size();
        return numberGeneralPlace - numberPlaceOrders;
    }

    @Override
    public int getNumberFreeMasters(ArrayList<Order> orders) {
        int numberMastersOrders = 0;
        int numberGeneralMasters = this.carOfficeRepository.getMasters().size();
        for (Order order : orders)
            numberMastersOrders += order.getMasters().size();
        return numberGeneralMasters - numberMastersOrders;
    }
}