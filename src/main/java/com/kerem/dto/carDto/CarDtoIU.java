package com.kerem.dto.carDto;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Car;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoIU {

    @NotNull(message = "licensePlateNumber cannot be null or empty")
    private String licensePlateNumber;

    @NotNull(message = "numberOfSeats cannot be null or empty")
    private Integer numberOfSeats;

    @NotNull(message = "brand cannot be null or empty")
    private String brand;

    @NotNull(message = "model cannot be null or empty")
    private String model;

    @NotNull(message = "mileage cannot be null or empty")
    private Long mileage;

    @NotNull(message = "transmissionType cannot be null or empty")
    private Car.TransmissionType transmissionType;

    @NotNull(message = "dailyPrice cannot be null or empty")
    private Double dailyPrice;

    @NotNull(message = "categoryType cannot be null or empty")
    private Car.Category categoryType;

    @NotNull(message = "categoryDescription cannot be null or empty")
    private String categoryDescription;

    @NotNull(message = "location cannot be null or empty")
    private LocationDto location;

    @NotNull(message = "status cannot be null or empty")
    private Car.CarStatus status;
}
