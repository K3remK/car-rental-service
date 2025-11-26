package com.kerem.controller.impl;

import com.kerem.controller.ICarController;
import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.SearchAvailableCarDto;
import com.kerem.dto.carDto.SearchCarParamsDto;
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

    @GetMapping(path = "/list/with-params")
    @Override
    public ResponseEntity<List<CarDto>> findCarsWithParams(@RequestBody @Valid SearchCarParamsDto searchCarParamsDto) {
        List<CarDto> foundCars = carService.findCarsWithParams(searchCarParamsDto);
        return ResponseEntity.ok(foundCars);
    }

    @GetMapping(path = "/list/available")
    @Override
    public ResponseEntity<List<CarDto>> searchAvailableCars(@RequestBody @Valid SearchAvailableCarDto searchAvailableCarDto) {
        SearchCarParamsDto searchCarParamsDto = carMapper.map(searchAvailableCarDto);
        searchCarParamsDto.setStatus(Car.CarStatus.IN_SERVICE);
        searchCarParamsDto.setBrand(null);
        searchCarParamsDto.setLicensePlate(null);
        searchCarParamsDto.setModel(null);
        searchCarParamsDto.setMaxMileage(null);

        List<CarDto> foundCars = carService.findCarsWithParams(searchCarParamsDto);

        return ResponseEntity.ok(foundCars);
    }

    @DeleteMapping(path = "/delete/{barcode}")
    @Override
    public ResponseEntity<Boolean> deleteCar(@PathVariable(name = "barcode") UUID barcode) {
        return ResponseEntity.ok(carService.deleteCar(barcode));
    }

    @PutMapping(path = "/update/{barcode}")
    @Override
    public ResponseEntity<CarDto> updateCar(@PathVariable(name = "barcode") UUID barcode,@RequestBody @Valid CarDtoIU carDto) {
        return ResponseEntity.ok(carService.updateCar(barcode, carDto));
    }

    @PostMapping(path = "/save")
    @Override
    public ResponseEntity<CarDto> saveCar(@RequestBody @Valid CarDtoIU carDtoIU) {
        return ResponseEntity.ok(carService.saveCar(carDtoIU));
    }
}
