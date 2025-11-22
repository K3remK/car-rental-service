package com.kerem.dto.carDto;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private String barcode;

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
