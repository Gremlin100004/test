package com.senla.carservice.controller;

import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class CarOfficeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarOfficeController.class);
    @Autowired
    private CarOfficeService carOfficeService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private OrderService orderService;

    public CarOfficeController() {
    }

    public String getFreePlacesMastersByDate(String date) {
        LOGGER.info("Method getFreePlacesMastersByDate");
        LOGGER.trace("Parameter date: {}", date);
        Date dateFree = DateUtil.getDatesFromString(date, false);
        if (dateFree == null) {
            return "error date";
        }
        try {
            Date startDayDate = DateUtil.bringStartOfDayDate(dateFree);
            Long numberFreeMasters = masterService.getNumberFreeMastersByDate(startDayDate);
            Long numberFreePlace = placeService.getNumberFreePlaceByDate(startDayDate);
            return "- number free places in service: " + numberFreePlace + "\n- number free masters in service: " +
                   numberFreeMasters;
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getNearestFreeDate() {
        LOGGER.info("Method getNearestFreeDate");
        try {
            return "Nearest free date: " + DateUtil.getStringFromDate(carOfficeService.getNearestFreeDate(), false);
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String exportEntities() {
        LOGGER.info("Method exportEntities");
        try {
            carOfficeService.exportEntities();
            return "Export completed successfully!";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String importEntities() {
        LOGGER.info("Method importEntities");
        try {
            carOfficeService.importEntities();
            return "Imported completed successfully!";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }
}