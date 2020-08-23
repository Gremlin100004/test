package com.senla.carservice.dao;

import com.senla.carservice.domain.Place;

import java.util.Date;
import java.util.List;

public interface PlaceDao extends GenericDao {
    List<Place> getFreePlaces(Date startDayDate);

    int getNumberPlace();
}