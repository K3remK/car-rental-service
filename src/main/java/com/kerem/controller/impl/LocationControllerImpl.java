package com.kerem.controller.impl;

import com.kerem.controller.ILocationController;
import com.kerem.dto.locationDto.LocationDto;
import com.kerem.dto.locationDto.LocationDtoIU;
import com.kerem.service.ILocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rentacar/api/locations")
@RequiredArgsConstructor
public class LocationControllerImpl implements ILocationController {

    private final ILocationService locationService;

    @GetMapping
    @Override
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping(path = "/{code}")
    @Override
    public ResponseEntity<LocationDto> getLocationByCode(@PathVariable(name = "code") Long code) {
        return ResponseEntity.ok(locationService.getLocationById(code));
    }

    @PostMapping
    @Override
    public ResponseEntity<LocationDto> saveLocation(@RequestBody @Valid LocationDtoIU locationDto) {
        return ResponseEntity.ok(locationService.saveLocation(locationDto));
    }

    @PutMapping(path = "/{code}")
    @Override
    public ResponseEntity<LocationDto> updateLocation(@PathVariable(name = "code") Long code, @RequestBody @Valid LocationDtoIU locationDto) {
        return ResponseEntity.ok(locationService.updateLocation(code, locationDto));
    }
}
