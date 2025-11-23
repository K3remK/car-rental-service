package com.kerem.service;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.entities.Car;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ICarService {

    List<CarDto> findCarsWithParams(
            String carCategory,
            String carBrand,
            String transmissionType,
            String status,
            Double minPrice,
            Double maxPrice,
            String licensePlateNumber,
            Long mileage,
            String model,
            LocalDateTime pickUpDate,
            LocalDateTime dropOffDate,
            Integer numberOfSeats,
            Long locationCode
    );

    Boolean isAvailable(UUID id, LocalDateTime pickUpDateAndTime, LocalDateTime dropOffDateAndTime);

    Car findCarById(UUID id);
    CarDto saveCar(CarDtoIU newCar);
    CarDto updateCar(UUID barcode, CarDtoIU updatedCar);
    Boolean deleteCar(String barcode);
}
