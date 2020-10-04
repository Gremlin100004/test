package com.senla.carservice.service.util;

import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Place;
import com.senla.carservice.dto.PlaceDto;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceMapper {

    public static Place transferDataFromPlaceDtoToPLace(PlaceDto placeDto, PlaceDao placeDao) {
        Place place = placeDao.findById(placeDto.getId());
        place.setNumber(placeDto.getNumber());
        place.setIsBusy(placeDto.getIsBusy());
        place.setDeleteStatus(placeDto.getDeleteStatus());
        return place;
    }

    public static List<PlaceDto> transferDataFromPlaceToPLaceDto(List<Place> places) {
        return places.stream()
            .map(PlaceMapper::transferDataFromPlaceToPLaceDto)
            .collect(Collectors.toList());
    }

    public static PlaceDto transferDataFromPlaceToPLaceDto(Place place) {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(place.getId());
        placeDto.setNumber(place.getNumber());
        placeDto.setIsBusy(place.getIsBusy());
        placeDto.setDeleteStatus(place.getDeleteStatus());
        return placeDto;
    }
}