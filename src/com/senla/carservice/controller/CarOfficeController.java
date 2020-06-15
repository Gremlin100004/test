package com.senla.carservice.controller;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.CarOfficeServiceImpl;
import com.senla.carservice.service.GarageService;
import com.senla.carservice.service.GarageServiceImpl;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CarOfficeController {
    private final CarOfficeService carOfficeService;
    private final OrderService orderService;
    private final MasterService masterService;
    private final GarageService garageService;

    public CarOfficeController() {
        this.carOfficeService = CarOfficeServiceImpl.getInstance();
        this.orderService = OrderServiceImpl.getInstance();
        this.masterService = MasterServiceImpl.getInstance();
        this.garageService = GarageServiceImpl.getInstance();
    }

    public ArrayList<Garage> getGarageFreePlace(String stringExecuteDate, String stringLeadDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date executeDate;
        Date leadDate;
        try {
            executeDate = format.parse(stringExecuteDate);
            leadDate = format.parse(stringLeadDate);
        } catch (ParseException e) {
            return new ArrayList<>();
        }
        ArrayList<Garage> freeGarages = new ArrayList<>(this.garageService.getGarages());
        ArrayList<Order> orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, executeDate, leadDate);
        for (Order order : orders) {
            for (Garage garage: freeGarages){
                if (garage.equals(order.getGarage())){
                    garage.getPlaces().remove(order.getPlace());
                    break;
                }
            }
        }
        return freeGarages;
    }

    public ArrayList<Master> getFreeMasters(String stringExecuteDate, String stringLeadDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date executeDate;
        Date leadDate;
        ArrayList<Master> freeMasters = new ArrayList<>(this.masterService.getMasters());
        try {
            executeDate = format.parse(stringExecuteDate);
            leadDate = format.parse(stringLeadDate);
        } catch (ParseException e) {
            return freeMasters;
        }
        ArrayList<Order> orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, executeDate, leadDate);
        for (Order order : orders) {
            order.getMasters().forEach(freeMasters::remove);
        }
        return freeMasters;
    }

    public String getFreePlacesByDate(String date) {
        final int hour = 23;
        final int minute = 59;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date dateFree;
        try {
            dateFree = format.parse(date);
        } catch (ParseException e) {
            return "error date";
        }
        Date endDay = DateUtil.addHourMinutes(dateFree, hour, minute);
        ArrayList<Order> orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, dateFree, endDay);
        int numberFreeMasters = this.carOfficeService.getNumberFreeMasters(orders);
        int numberFreePlace = this.carOfficeService.getNumberFreePlaceDate(orders);
        return String.format("- number free places in service: %s\n- number free masters in service: %s", numberFreePlace, numberFreeMasters);
    }

    public String getNearestFreeDate() {
        final int hour = 23;
        final int minute = 59;
        Date dateFree = DateUtil.getDateWithoutTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        if (this.masterService.getMasters().size() <2 &&  this.garageService.getGarages().size() < 1){
            return "Error!!! Add masters, garage and place to service!";
        }
        while (true) {
            Date endDay = DateUtil.addHourMinutes(dateFree, hour, minute);
            ArrayList<Order> orders = this.orderService.getOrders();
            orders = this.orderService.sortOrderByPeriod(orders, dateFree, endDay);
            int numberFreeMasters = this.carOfficeService.getNumberFreeMasters(orders);
            int numberFreePlace = this.carOfficeService.getNumberFreePlaceDate(orders);
            if (numberFreeMasters > 1 && numberFreePlace > 0) {
                break;
            }
            dateFree = DateUtil.addDays(dateFree, 1);
        }
        return String.format("Nearest free date: %s", dateFormat.format(dateFree.getTime()));
    }
}