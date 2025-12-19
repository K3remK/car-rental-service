package com.kerem;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.dto.locationDto.LocationDtoIU;
import com.kerem.entities.Location;
import com.kerem.repository.LocationRepository;
import com.kerem.service.ILocationService;
import com.kerem.starter.CarRentalServiceApplication;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = CarRentalServiceApplication.class)
@ActiveProfiles("test")
@Transactional
public class LocationServiceTest {

    @Autowired
    private ILocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void testSaveLocation() {
        // Arrange
        LocationDtoIU locationDtoIU = new LocationDtoIU();
        locationDtoIU.setLocationName("Istanbul Airport");

        // Act
        LocationDto savedLocation = locationService.saveLocation(locationDtoIU);

        // Assert
        Assertions.assertNotNull(savedLocation.getCode());
        Assertions.assertEquals("Istanbul Airport", savedLocation.getLocationName());

        // Verify in DB
        Assertions.assertTrue(locationRepository.findById(savedLocation.getCode()).isPresent());
    }

    @Test
    void testGetLocations() {
        // Arrange
        Location loc1 = new Location();
        loc1.setLocationName("Sabiha Gokcen");
        locationRepository.save(loc1);

        Location loc2 = new Location();
        loc2.setLocationName("Esenboga");
        locationRepository.save(loc2);

        // Act
        List<LocationDto> locations = locationService.getAllLocations();

        // Assert
        Assertions.assertTrue(locations.size() >= 2);
    }

    @Test
    void testGetLocationById() {
        // Arrange
        Location loc = new Location();
        loc.setLocationName("Antalya Airport");
        Location saved = locationRepository.save(loc);

        // Act
        LocationDto found = locationService.getLocationById(saved.getCode());

        // Assert
        Assertions.assertEquals("Antalya Airport", found.getLocationName());
    }

    @Test
    void testGetLocationById_NotFound() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> locationService.getLocationById(9999L));
    }

    @Test
    void testUpdateLocation() {
        // Arrange
        Location loc = new Location();
        loc.setLocationName("Old Name");
        Location saved = locationRepository.save(loc);

        LocationDtoIU updateDto = new LocationDtoIU();
        updateDto.setLocationName("New Name");

        // Act
        LocationDto updated = locationService.updateLocation(saved.getCode(), updateDto);

        // Assert
        Assertions.assertEquals("New Name", updated.getLocationName());

        // Verify DB
        Location dbLocation = locationRepository.findById(saved.getCode()).orElseThrow();
        Assertions.assertEquals("New Name", dbLocation.getLocationName());
    }
}