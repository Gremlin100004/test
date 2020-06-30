package com.senla.carservice.service;

import com.senla.carservice.csv.CsvPlace;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.repository.PlaceRepositoryImpl;
import com.senla.carservice.util.DateUtil;

import java.util.Date;
import java.util.List;

public class PlaceServiceImpl implements PlaceService {
    private static PlaceService instance;
    private final PlaceRepository placeRepository;

    private PlaceServiceImpl() {
        placeRepository = PlaceRepositoryImpl.getInstance();
    }

    public static PlaceService getInstance() {
        if (instance == null) {
            instance = new PlaceServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Place> getPlaces() {
        checkPlaces();
        return placeRepository.getPlaces();
    }

    @Override
    public void addPlace(Integer number) {
        placeRepository.addPlace(new Place(number));
    }

    @Override
    public void deletePlace(Place place) {
        placeRepository.deletePlace(place);
    }

    @Override
    public List<Place> getFreePlaces() {
        if (placeRepository.getCurrentFreePlaces().isEmpty())
            throw new NumberObjectZeroException("There are no places");
        return placeRepository.getCurrentFreePlaces();
    }

    @Override
    public int getNumberFreePlaceByDate(Date executeDate, Date leadDate, List<Order> orders) {
        DateUtil.checkDateTime(executeDate, leadDate);
        checkPlaces();
        return placeRepository.getFreePlaces(orders).size();
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate, Date leadDate, List<Order> orders) {
        DateUtil.checkDateTime(executeDate, leadDate);
        checkPlaces();
        if (placeRepository.getFreePlaces(orders).isEmpty())
            throw new NumberObjectZeroException("There are no free places");
        return placeRepository.getFreePlaces(orders);
    }

    private void checkPlaces() {
        if (placeRepository.getPlaces().isEmpty()) throw new NumberObjectZeroException("There are no places");
    }

    @Override
    public String exportPlaces() {
        checkPlaces();
        return CsvPlace.exportPlaces(placeRepository.getPlaces());
    }

    @Override
    public String importPlaces() {
        return CsvPlace.importPlaces();
    }
}