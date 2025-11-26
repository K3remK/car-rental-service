package com.kerem.dto.carDto;

import com.kerem.entities.Car;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCarParamsDto {

    Car.Category carCategory;

    String brand;

    Car.TransmissionType transmissionType;

    Car.CarStatus status;

    @Positive(message = "Price must be positive")
    Double minPrice;

    @Positive(message = "Price must be positive")
    Double maxPrice;

    String licensePlate;

    Long maxMileage;

    String model;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime pickUpDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime dropOffDate;

    Integer numberOfSeats;

    Long pickUpLocationCode;

}
