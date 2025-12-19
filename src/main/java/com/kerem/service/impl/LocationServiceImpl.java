package com.kerem.service.impl;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.dto.locationDto.LocationDtoIU;
import com.kerem.entities.Location;
import com.kerem.mapper.LocationMapper;
import com.kerem.repository.LocationRepository;
import com.kerem.service.ILocationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements ILocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public List<LocationDto> getAllLocations() {

        List<Location> locations = locationRepository.findAll();

        if (locations.isEmpty()) {
            throw new EntityNotFoundException("Location not found");
        }

        return locations.stream()
                .map(locationMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDto getLocationById(Long id) {
        return locationMapper.map(locationRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Location not found! ID:" + id)));
    }

    @Transactional
    @Override
    public LocationDto saveLocation(LocationDtoIU locationDtoIU) {
        return locationMapper.map(locationRepository.save(locationMapper.map(locationDtoIU)));
    }

    @Transactional
    @Override
    public LocationDto updateLocation(Long id, LocationDtoIU locationDtoIU) {

        Location dbLocation = locationRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Location not found! ID:" + id));

        locationMapper.map(locationDtoIU, dbLocation);

        return locationMapper.map(locationRepository.save(dbLocation));
    }

    @Override
    public Location getReferenceById(Long code) {
        return locationRepository.getReferenceById(code);
    }
}
