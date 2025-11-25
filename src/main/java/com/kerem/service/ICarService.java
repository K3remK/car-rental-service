package com.kerem.service;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.SearchCarParamsDto;
import com.kerem.entities.Car;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ICarService {

    List<CarDto> findCarsWithParams(SearchCarParamsDto searchCarParamsDto);
    Boolean isAvailable(UUID barcode, LocalDateTime pickUpDateAndTime, LocalDateTime dropOffDateAndTime);
    Car findCarById(UUID barcode);
    CarDto updateCar(UUID barcode, CarDtoIU updatedCar);
    Boolean deleteCar(UUID barcode);
}
