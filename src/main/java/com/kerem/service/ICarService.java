package com.kerem.service;

import com.kerem.dto.carDto.CarGetRequestDto;
import com.kerem.entities.Car;
import com.kerem.entities.Location;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ICarService {

    List<CarGetRequestDto> findCarsWithParams(
            String carCategory,
            String carBrand,
            String transmissionType,
            String status,
            Double minPrice,
            Double maxPrice,
            String licensePlateNumber,
            Long mileage,
            String model,
            Date pickUpDate,
            Date dropOffDate,
            Integer numberOfSeats,
            Long locationCode
    );

    Boolean deleteCar(String barcode);
}
