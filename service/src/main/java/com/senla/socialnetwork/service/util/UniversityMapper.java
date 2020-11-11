package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.dto.UniversityDto;

public class UniversityMapper {
    public static UniversityDto getUniversityDto(University university) {
        UniversityDto universityDto = new UniversityDto();
        universityDto.setId(university.getId());
        universityDto.setName(university.getName());
        universityDto.setLocation(LocationMapper.getLocationDto(university.getLocation()));
        return universityDto;
    }

    public static University getUniversity(UniversityDto universityDto,
                                           UniversityDao universityDao,
                                           LocationDao locationDao) {
        University university;
        if (universityDto.getId() == null) {
            university = new University();
        } else {
            university = universityDao.findById(universityDto.getId());
        }
        university.setName(universityDto.getName());
        university.setLocation(LocationMapper.getLocation(universityDto.getLocation(), locationDao));
        return university;
    }

}