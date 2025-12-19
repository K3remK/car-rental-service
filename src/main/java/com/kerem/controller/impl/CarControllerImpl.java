package com.kerem.controller.impl;

import com.kerem.controller.ICarController;
import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.SearchAvailableCarDto;
import com.kerem.dto.carDto.SearchCarWithParamsDto;
import com.kerem.entities.Car;
import com.kerem.mapper.CarMapper;
import com.kerem.service.ICarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/rentacar/api/cars")
@RequiredArgsConstructor
public class CarControllerImpl implements ICarController {

    private final ICarService carService;
    private final CarMapper carMapper;

    @PostMapping(path = "/search")
    @Override
    public ResponseEntity<List<CarDto>> findCarsWithParams(
            @RequestBody @Valid SearchCarWithParamsDto searchCarWithParamsDto) {
        List<CarDto> foundCars = carService.findCarsWithParams(searchCarWithParamsDto);
        return ResponseEntity.ok(foundCars);
    }

    @PostMapping(path = "/search/available")
    @Override
    public ResponseEntity<List<CarDto>> searchAvailableCars(
            @RequestBody @Valid SearchAvailableCarDto searchAvailableCarDto) {
        SearchCarWithParamsDto searchCarWithParamsDto = carMapper.map(searchAvailableCarDto);
        searchCarWithParamsDto.setStatus(Car.CarStatus.IN_SERVICE);
        searchCarWithParamsDto.setBrand(null);
        searchCarWithParamsDto.setLicensePlate(null);
        searchCarWithParamsDto.setModel(null);
        searchCarWithParamsDto.setMaxMileage(null);

        List<CarDto> foundCars = carService.findCarsWithParams(searchCarWithParamsDto);

        return ResponseEntity.ok(foundCars);
    }

    @GetMapping(path = "/{barcode}")
    @Override
    public ResponseEntity<CarDto> getCarByBarcode(@PathVariable(name = "barcode") UUID barcode) {
        return ResponseEntity.ok(carService.findCarById(barcode));
    }

    @DeleteMapping(path = "/{barcode}")
    @Override
    public ResponseEntity<Boolean> deleteCar(@PathVariable(name = "barcode") UUID barcode) {
        return ResponseEntity.ok(carService.deleteCar(barcode));
    }

    @PutMapping(path = "/{barcode}")
    @Override
    public ResponseEntity<CarDto> updateCar(@PathVariable(name = "barcode") UUID barcode,
            @RequestBody @Valid CarDtoIU carDto) {
        return ResponseEntity.ok(carService.updateCar(barcode, carDto));
    }

    @PostMapping
    @Override
    public ResponseEntity<CarDto> saveCar(@RequestBody @Valid CarDtoIU carDtoIU) {
        return ResponseEntity.ok(carService.saveCar(carDtoIU));
    }
}
