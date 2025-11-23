package com.kerem.controller.impl;

import com.kerem.controller.ICarController;
import com.kerem.dto.carDto.CarDto;
import com.kerem.service.ICarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/*
* Code,Constant,Meaning,When to use
    200,HttpStatus.OK,OK,Standard successful GET/PUT.
    201,HttpStatus.CREATED,Created,Successful POST (creation).
    204,HttpStatus.NO_CONTENT,No Content,Successful DELETE (nothing to return).
    400,HttpStatus.BAD_REQUEST,Bad Request,Validation failed or invalid input.
    401,HttpStatus.UNAUTHORIZED,Unauthorized,User is not logged in.
    403,HttpStatus.FORBIDDEN,Forbidden,User is logged in but lacks permission.
    404,HttpStatus.NOT_FOUND,Not Found,Resource doesn't exist.
    500,HttpStatus.INTERNAL_SERVER_ERROR,Server Error,Uncaught exception/crash.
*
* */


@RestController
@RequestMapping(path = "/rentacar/api/cars")
@RequiredArgsConstructor
public class CarControllerImpl implements ICarController {

    private final ICarService carService;

    @GetMapping(path = "/list/with-params")
    @Override
    public ResponseEntity<List<CarDto>> findCarsWithParams(
            @RequestParam(name = "carCategory", required = false) String carCategory,
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "transmissionType", required = false) String transmissionType,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "licensePlate", required = false) String licensePlate,
            @RequestParam(name = "maxMileage", required = false) Long maxMileage,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "pickUpDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime pickUpDate,
            @RequestParam(name = "dropOffDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime dropOffDate,
            @RequestParam(name = "numberOfSeats", required = false) Integer numberOfSeats,
            @RequestParam(name = "pickUpLocationCode", required = false) Long pickUpLocationCode
    ) {
        List<CarDto> foundCars = carService.findCarsWithParams(
                carCategory,
                brand,
                transmissionType,
                status,
                minPrice,
                maxPrice,
                licensePlate,
                maxMileage,
                model,
                pickUpDate,
                dropOffDate,
                numberOfSeats,
                pickUpLocationCode
        );

        if (foundCars.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundCars);
    }

    @GetMapping(path = "/list/available")
    @Override
    public ResponseEntity<List<CarDto>> searchAvailableCars(
            @RequestParam(name = "carCategory", required = false) String carCategory,
            @RequestParam(name = "transmissionType", required = false) String transmissionType,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "pickUpDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm") LocalDateTime pickUpDate,
            @RequestParam(name = "dropOffDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm") LocalDateTime dropOffDate,
            @RequestParam(name = "numberOfSeats", required = false) Integer numberOfSeats,
            @RequestParam(name = "pickUpLocationCode", required = true) Long pickUpLocationCode
    ) {
        List<CarDto> foundCars = carService.findCarsWithParams(
                carCategory,
                null,
                transmissionType,
                "IN_SERVICE",
                minPrice,
                maxPrice,
                null,
                null,
                null,
                pickUpDate,
                dropOffDate,
                numberOfSeats,
                pickUpLocationCode
        );

        if (foundCars.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundCars);
    }

    @DeleteMapping(path = "/delete/{barcode}")
    @Override
    public ResponseEntity<Boolean> deleteCar(@PathVariable(name = "barcode") UUID barcode) {
        return ResponseEntity.ok(carService.deleteCar(barcode));
    }
}
