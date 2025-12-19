package com.kerem.controller;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.SearchAvailableCarDto;
import com.kerem.dto.carDto.SearchCarWithParamsDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ICarController {
    ResponseEntity<List<CarDto>> findCarsWithParams(SearchCarWithParamsDto searchCarWithParamsDto);

    ResponseEntity<List<CarDto>> searchAvailableCars(SearchAvailableCarDto searchAvailableCarDto);

    ResponseEntity<Boolean> deleteCar(UUID barcode);

    ResponseEntity<CarDto> updateCar(UUID barcode, CarDtoIU carDto);
    ResponseEntity<CarDto>  saveCar(CarDtoIU carDtoIU);

    ResponseEntity<CarDto> getCarByBarcode(UUID barcode);

}
