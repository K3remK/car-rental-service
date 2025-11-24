package com.kerem;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.entities.Car;
import com.kerem.entities.Location;
import com.kerem.mapper.CarMapper;
import com.kerem.repository.CarRepository;
import com.kerem.service.impl.CarServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarServiceImpl carService;

    // --- Helper Methods to Generate Random Data ---

    private Car createRandomCar() {
        Car car = new Car();
        car.setBarcode(UUID.randomUUID());
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setDailyPrice(100.0);
        car.setLicensePlateNumber("34ABC" + new Random().nextInt(999));
        car.setStatus(Car.CarStatus.IN_SERVICE);

        Location loc = new Location();
        loc.setCode(1L);
        loc.setLocationName("Airport");
        car.setLocation(loc);

        return car;
    }

    private CarDto createCarDto(Car car) {
        CarDto dto = new CarDto();
        dto.setBarcode(car.getBarcode());
        dto.setBrand(car.getBrand());
        dto.setDailyPrice(car.getDailyPrice());
        return dto;
    }

    // --- Tests ---

    @Test
    @DisplayName("Find Car By ID - Success")
    void testFindCarById_Success() {
        // Arrange
        Car mockCar = createRandomCar();
        when(carRepository.findById(mockCar.getBarcode())).thenReturn(Optional.of(mockCar));

        // Act
        Car result = carService.findCarById(mockCar.getBarcode());

        // Assert
        assertNotNull(result);
        assertEquals(mockCar.getBarcode(), result.getBarcode());
        verify(carRepository).findById(mockCar.getBarcode());
    }

    @Test
    @DisplayName("Find Car By ID - Not Found Throws Exception")
    void testFindCarById_NotFound() {
        // Arrange
        UUID randomId = UUID.randomUUID();
        when(carRepository.findById(randomId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> carService.findCarById(randomId));
    }

    @Test
    @DisplayName("Update Car - Success")
    void testUpdateCar_Success() {
        // Arrange
        Car existingCar = createRandomCar();
        CarDtoIU updateInfo = new CarDtoIU(); // Assuming DTO has setters
        // updateInfo.setBrand("Honda");

        CarDto expectedDto = createCarDto(existingCar);

        when(carRepository.findById(existingCar.getBarcode())).thenReturn(Optional.of(existingCar));
        // Mapper void method doesn't return anything, checks side effects usually or we assume it works
        doNothing().when(carMapper).map(updateInfo, existingCar);
        when(carRepository.save(existingCar)).thenReturn(existingCar);
        when(carMapper.mapGet(existingCar)).thenReturn(expectedDto);

        // Act
        CarDto result = carService.updateCar(existingCar.getBarcode(), updateInfo);

        // Assert
        assertNotNull(result);
        verify(carRepository).save(existingCar);
        verify(carMapper).map(updateInfo, existingCar);
    }

    @Test
    @DisplayName("Delete Car - Success (Car not in use)")
    void testDeleteCar_Success() {
        // Arrange
        Car car = createRandomCar();
        when(carRepository.findById(car.getBarcode())).thenReturn(Optional.of(car));

        // Mocking the "Usage Check" specification query.
        // We return an empty list, implying no reservations found for this car logic.
        when(carRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        // Act
        Boolean result = carService.deleteCar(car.getBarcode());

        // Assert
        assertTrue(result);
        verify(carRepository).delete(car);
    }

    @Test
    @DisplayName("Delete Car - Fail (Car is currently used/reserved)")
    void testDeleteCar_Fail_InUse() {
        // Arrange
        Car car = createRandomCar();
        when(carRepository.findById(car.getBarcode())).thenReturn(Optional.of(car));

        // The service logic checks if the returned list contains the car.
        // So we simulate the DB finding this car in the "HasBeenUsed" spec.
        when(carRepository.findAll(any(Specification.class))).thenReturn(List.of(car));

        // Act & Assert
        assertThrows(NotAcceptableStatusException.class, () -> carService.deleteCar(car.getBarcode()));

        // Verify delete was NEVER called
        verify(carRepository, never()).delete(any(Car.class));
    }

    @Test
    @DisplayName("Find Cars With Params - Success")
    void testFindCarsWithParams_Success() {
        // Arrange
        Car car1 = createRandomCar();
        Car car2 = createRandomCar();
        List<Car> carList = List.of(car1, car2);

        // Since specifications are complex static chains, we strictly test
        // that the repository is called and handles the result list correctly.
        when(carRepository.findAll(any(Specification.class))).thenReturn(carList);
        when(carMapper.mapGet(any(Car.class))).thenReturn(new CarDto());

        // Act
        List<CarDto> results = carService.findCarsWithParams(
                "SUV", "Toyota", "AUTOMATIC", "IN_SERVICE",
                50.0, 200.0, null, 10000L, null,
                LocalDateTime.now(), LocalDateTime.now().plusDays(3), 4, 1L
        );

        // Assert
        assertEquals(2, results.size());
        verify(carRepository).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Find Cars With Params - Empty Result Throws Exception")
    void testFindCarsWithParams_NotFound() {
        // Arrange
        when(carRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> carService.findCarsWithParams(
                null, null, null, null, null, null, null, null, null, null, null, null, null
        ));
    }
}