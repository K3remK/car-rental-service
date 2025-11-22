package com.kerem.controller;

import com.kerem.dto.carDto.CarDto;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

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
            Date pickUpDate,
            Date dropOffDate,
            Integer numberOfSeats,
            Long locationCode
    );

    ResponseEntity<List<CarDto>> searchAvailableCars(
            String carCategory,
            String transmissionType,
            Double minPrice,
            Double maxPrice,
            Date pickUpDate,
            Date dropOffDate,
            Integer numberOfSeats,
            Long locationCode
    );
}
