package com.kerem.service;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.dto.locationDto.LocationDtoIU;
import com.kerem.entities.Location;

import java.util.List;

public interface ILocationService {

    List<LocationDto> getLocations();
    LocationDto getLocationById(Long id);
    LocationDto saveLocation(LocationDtoIU locationDtoIU);
    LocationDto updateLocation(Long id, LocationDtoIU locationDtoIU);
    Location getReferenceById(Long code);
}
