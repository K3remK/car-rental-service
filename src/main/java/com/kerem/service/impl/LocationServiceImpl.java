package com.kerem.service.impl;

import com.kerem.entities.Location;
import com.kerem.repository.LocationRepository;
import com.kerem.service.ILocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements ILocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location getLocationById(Long id) {
        Location location = locationRepository.findById(id).orElse(null);

        if (location == null) {
            // TODO: throw not found exception
        }

        return location;
    }
}
