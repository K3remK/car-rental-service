package com.kerem.controller;

import com.kerem.dto.carDto.CarGetRequestDto;
import com.kerem.entities.Car;
import com.kerem.entities.Location;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface ICarController {
    ResponseEntity<List<CarGetRequestDto>> findCarsWithParams(
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

    ResponseEntity<List<CarGetRequestDto>> searchAvailableCars(
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
