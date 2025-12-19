package com.kerem.controller;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.dto.locationDto.LocationDtoIU;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ILocationController {

    ResponseEntity<List<LocationDto>> getAllLocations();
    ResponseEntity<LocationDto> getLocationByCode(Long code);
    ResponseEntity<LocationDto> saveLocation(LocationDtoIU locationDto);
    ResponseEntity<LocationDto> updateLocation(Long code, LocationDtoIU locationDto);
}
