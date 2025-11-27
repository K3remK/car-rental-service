package com.kerem.dto.carDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Car;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDto {

    private UUID barcode;

    private String licensePlateNumber;

    private Integer numberOfSeats;

    private String brand;

    private String model;

    private Long mileage;

    private Car.TransmissionType transmissionType;

    private Double dailyPrice;

    private Car.Category categoryType;

    private String categoryDescription;

    private LocationDto location;

    private Car.CarStatus status;
}
