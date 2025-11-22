package com.kerem.service;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;

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
            Date pickUpDate,
            Date dropOffDate,
            Integer numberOfSeats,
            Long locationCode
    );

    CarDto saveCar(CarDtoIU newCar);
    CarDto updateCar(UUID barcode, CarDtoIU updatedCar);
    Boolean deleteCar(String barcode);
}
