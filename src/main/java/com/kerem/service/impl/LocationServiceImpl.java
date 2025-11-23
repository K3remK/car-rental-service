package com.kerem.service.impl;

import com.kerem.entities.Location;
import com.kerem.repository.LocationRepository;
import com.kerem.service.ILocationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements ILocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Location not found! ID:" + id));
    }
}
