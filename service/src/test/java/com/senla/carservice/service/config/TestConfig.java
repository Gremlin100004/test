package com.senla.carservice.service.config;

import com.senla.carservice.csv.CsvMaster;
import com.senla.carservice.csv.CsvOrder;
import com.senla.carservice.csv.CsvPlace;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.senla.carservice.service")
public class TestConfig {

    @Bean
    MasterDao masterDao() {
        return Mockito.mock(MasterDao.class);
    }

    @Bean
    OrderDao orderDao() {
        return Mockito.mock(OrderDao.class);
    }

    @Bean
    PlaceDao placeDao() {
        return Mockito.mock(PlaceDao.class);
    }

    @Bean
    CsvPlace csvPlace() {
        return Mockito.mock(CsvPlace.class);
    }

    @Bean
    CsvOrder csvOrder() {
        return Mockito.mock(CsvOrder.class);
    }

    @Bean
    CsvMaster csvMaster() {
        return Mockito.mock(CsvMaster.class);
    }
}