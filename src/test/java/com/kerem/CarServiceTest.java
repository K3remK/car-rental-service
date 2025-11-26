package com.kerem;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.SearchCarParamsDto;
import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Car;
import com.kerem.entities.Location;
import com.kerem.repository.CarRepository;
import com.kerem.repository.LocationRepository;
import com.kerem.service.ICarService;
import com.kerem.starter.CarRentalServiceApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = CarRentalServiceApplication.class)
@ActiveProfiles("test")
@Transactional
public class CarServiceTest {

    @Autowired
    private ICarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private LocationRepository locationRepository;

    private Location testLocation;

    @BeforeEach
    void setUp() {
        testLocation = new Location();
        testLocation.setLocationName("Test Hub");
        testLocation = locationRepository.save(testLocation);
    }

    @Test
    void testSaveCar() {
        // Arrange
        CarDtoIU carDtoIU = new CarDtoIU();
        carDtoIU.setBrand("Toyota");
        carDtoIU.setModel("Corolla");
        carDtoIU.setLicensePlateNumber("34ABC123");
        carDtoIU.setDailyPrice(100.0);
        carDtoIU.setMileage(1000L);
        carDtoIU.setTransmissionType(Car.TransmissionType.AUTOMATIC);
        carDtoIU.setCategoryType(Car.Category.COMPACT);
        carDtoIU.setCategoryDescription("Compact Car");
        carDtoIU.setStatus(Car.CarStatus.IN_SERVICE);
        carDtoIU.setNumberOfSeats(5);
        carDtoIU.setLocationCode(testLocation.getCode());

        // Act
        CarDto savedCar = carService.saveCar(carDtoIU);

        // Assert
        Assertions.assertNotNull(savedCar.getBarcode());
        Assertions.assertEquals("Toyota", savedCar.getBrand());
        Assertions.assertEquals(testLocation.getLocationName(), savedCar.getLocation().getLocationName());
    }

    @Test
    void testFindCarById() {
        // Arrange
        Car car = new Car();
        car.setBrand("Honda");
        car.setModel("Civic");
        car.setLicensePlateNumber("34XYZ789");
        car.setDailyPrice(120.0);
        car.setTransmissionType(Car.TransmissionType.MANUAL);
        car.setCategoryType(Car.Category.MID_SIZE);
        car.setCategoryDescription("MidSize");
        car.setStatus(Car.CarStatus.IN_SERVICE);
        car.setLocation(testLocation);
        Car saved = carRepository.save(car);

        // Act
        CarDto found = carService.findCarById(saved.getBarcode());

        // Assert
        Assertions.assertEquals("Honda", found.getBrand());
    }

    @Test
    void testUpdateCar() {
        // Arrange
        Car car = new Car();
        car.setBrand("BMW");
        car.setModel("320i");
        car.setLicensePlateNumber("34BMW001");
        car.setDailyPrice(200.0);
        car.setTransmissionType(Car.TransmissionType.AUTOMATIC);
        car.setCategoryType(Car.Category.LUXURY);
        car.setCategoryDescription("Luxury");
        car.setStatus(Car.CarStatus.IN_SERVICE);
        car.setLocation(testLocation);
        Car saved = carRepository.save(car);

        CarDtoIU updateDto = new CarDtoIU();
        updateDto.setBrand("BMW");
        updateDto.setModel("520i"); // Changed
        updateDto.setLicensePlateNumber("34BMW001");
        updateDto.setDailyPrice(250.0); // Changed
        updateDto.setTransmissionType(Car.TransmissionType.AUTOMATIC);
        updateDto.setCategoryType(Car.Category.LUXURY);
        updateDto.setCategoryDescription("Luxury");
        updateDto.setStatus(Car.CarStatus.IN_SERVICE);
        updateDto.setLocationCode(testLocation.getCode());

        // Act
        CarDto updated = carService.updateCar(saved.getBarcode(), updateDto);

        // Assert
        Assertions.assertEquals("520i", updated.getModel());
        Assertions.assertEquals(250.0, updated.getDailyPrice());
    }

    @Test
    void testDeleteCar() {
        // Arrange
        Car car = new Car();
        car.setBrand("Ford");
        car.setModel("Fiesta");
        car.setLicensePlateNumber("34FRD111");
        car.setDailyPrice(80.0);
        car.setTransmissionType(Car.TransmissionType.MANUAL);
        car.setCategoryType(Car.Category.COMPACT);
        car.setCategoryDescription("Small");
        car.setStatus(Car.CarStatus.IN_SERVICE);
        car.setLocation(testLocation);
        Car saved = carRepository.save(car);

        // Act
        Boolean deleted = carService.deleteCar(saved.getBarcode());

        // Assert
        Assertions.assertTrue(deleted);
        Assertions.assertFalse(carRepository.findById(saved.getBarcode()).isPresent());
    }

    @Test
    void testFindCarsWithParams() {
        // Arrange: Insert a specific car to find
        Car car = new Car();
        car.setBrand("Mercedes");
        car.setModel("C200");
        car.setLicensePlateNumber("34MER999");
        car.setDailyPrice(300.0);
        car.setMileage(5000L);
        car.setNumberOfSeats(5);
        car.setTransmissionType(Car.TransmissionType.AUTOMATIC);
        car.setCategoryType(Car.Category.LUXURY);
        car.setCategoryDescription("Lux");
        car.setStatus(Car.CarStatus.IN_SERVICE);
        car.setLocation(testLocation);
        carRepository.save(car);

        SearchCarParamsDto params = new SearchCarParamsDto();
        params.setBrand("Mercedes");
        params.setMinPrice(200.0);
        params.setMaxPrice(400.0);
        params.setPickUpLocationCode(testLocation.getCode());

        // Act
        List<CarDto> results = carService.findCarsWithParams(params);

        // Assert
        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals("Mercedes", results.getFirst().getBrand());
    }
}