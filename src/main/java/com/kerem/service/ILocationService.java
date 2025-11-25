package com.kerem.service;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.dto.locationDto.LocationDtoIU;

import java.util.List;

public interface ILocationService {

    List<LocationDto> getLocations();
    LocationDto getLocationById(Long id);
    LocationDto saveLocation(LocationDtoIU locationDtoIU);
    LocationDto updateLocation(Long id, LocationDtoIU locationDtoIU);
}
