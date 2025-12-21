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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/rentacar/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Car Management", description = "APIs for managing cars")
public class CarControllerImpl implements ICarController {

        private final ICarService carService;
        private final CarMapper carMapper;

        @PostMapping(path = "/search")
        @Override
        @Operation(summary = "Search cars with params", description = "Finds cars based on specific criteria")
        @ApiResponse(responseCode = "200", description = "Cars found successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        @ApiResponse(responseCode = "404", description = "Cars not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<List<CarDto>> findCarsWithParams(
                        @Parameter(description = "Search criteria", required = true) @RequestBody @Valid SearchCarWithParamsDto searchCarWithParamsDto) {
                List<CarDto> foundCars = carService.findCarsWithParams(searchCarWithParamsDto);
                return ResponseEntity.ok(foundCars);
        }

        @PostMapping(path = "/search/available")
        @Override
        @Operation(summary = "Search available cars", description = "Finds cars that are available for rental")
        @ApiResponse(responseCode = "200", description = "Available cars found successfully")
        @ApiResponse(responseCode = "404", description = "Available cars not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        public ResponseEntity<List<CarDto>> searchAvailableCars(
                        @Parameter(description = "Availability search criteria", required = true) @RequestBody @Valid SearchAvailableCarDto searchAvailableCarDto) {
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
        @Operation(summary = "Get car by barcode", description = "Retrieves a specific car by its unique barcode")
        @ApiResponse(responseCode = "200", description = "Car retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Car not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<CarDto> getCarByBarcode(
                        @Parameter(description = "Barcode of the car", required = true) @PathVariable(name = "barcode") UUID barcode) {
                return ResponseEntity.ok(carService.findCarById(barcode));
        }

        @DeleteMapping(path = "/{barcode}")
        @Override
        @Operation(summary = "Delete car", description = "Removes a car from the system")
        @ApiResponse(responseCode = "200", description = "Car deleted successfully")
        @ApiResponse(responseCode = "404", description = "Car not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        @ApiResponse(responseCode = "406", description = "Car cannot be deleted", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<Boolean> deleteCar(
                        @Parameter(description = "Barcode of the car to delete", required = true) @PathVariable(name = "barcode") UUID barcode) {
                return ResponseEntity.ok(carService.deleteCar(barcode));
        }

        @PutMapping(path = "/{barcode}")
        @Override
        @Operation(summary = "Update car", description = "Updates an existing car")
        @ApiResponse(responseCode = "200", description = "Car updated successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        @ApiResponse(responseCode = "404", description = "Car not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<CarDto> updateCar(
                        @Parameter(description = "Barcode of the car to update", required = true) @PathVariable(name = "barcode") UUID barcode,
                        @Parameter(description = "Updated car details", required = true) @RequestBody @Valid CarDtoIU carDto) {
                return ResponseEntity.ok(carService.updateCar(barcode, carDto));
        }

        @PostMapping
        @Override
        @Operation(summary = "Create car", description = "Creates a new car entry")
        @ApiResponse(responseCode = "200", description = "Car created successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        public ResponseEntity<CarDto> saveCar(
                        @Parameter(description = "Car details", required = true) @RequestBody @Valid CarDtoIU carDtoIU) {
                return ResponseEntity.ok(carService.saveCar(carDtoIU));
        }
}
