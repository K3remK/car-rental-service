package com.kerem;

import com.kerem.entities.ExtraService;
import com.kerem.entities.Location;
import com.kerem.repository.LocationRepository;
import com.kerem.service.impl.LocationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.annotations.DialectOverride;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    @Test
    @DisplayName(value = "Get Location By ID - Success")
    void getLocationById_Success() {
        Location location = new Location();
        location.setCode(1L);

        when(locationRepository.findById(location.getCode())).thenReturn(Optional.of(location));

        Location result = locationService.getLocationById(location.getCode());

        assertNotNull(result);
        assertEquals(location.getCode(), result.getCode());
        verify(locationRepository).findById(location.getCode());
    }

    @Test
    @DisplayName(value = "Get Location By ID - Throw EntityNotFound Exception")
    void getLocationById_ThrowEntityNotFoundException() {
        Long nonExistentId = 1L;
        when(locationRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> locationService.getLocationById(nonExistentId));
    }
}
