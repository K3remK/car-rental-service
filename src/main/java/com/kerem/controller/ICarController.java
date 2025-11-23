package com.kerem.controller;

import com.kerem.dto.carDto.CarDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ICarController {
    ResponseEntity<List<CarDto>> findCarsWithParams(
            String carCategory,
            String carBrand,
            String transmissionType,
            String status,
            Double minPrice,
            Double maxPrice,
            String licensePlateNumber,
            Long maxMileage,
            String model,
            LocalDateTime pickUpDate,
            LocalDateTime dropOffDate,
            Integer numberOfSeats,
            Long locationCode
    );

    ResponseEntity<List<CarDto>> searchAvailableCars(
            String carCategory,
            String transmissionType,
            Double minPrice,
            Double maxPrice,
            LocalDateTime pickUpDate,
            LocalDateTime dropOffDate,
            Integer numberOfSeats,
            Long locationCode
    );

    ResponseEntity<Boolean> deleteCar(UUID barcode);

}
