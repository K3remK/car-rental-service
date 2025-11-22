package com.kerem.dto.carDto;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoIU {

    private String licensePlateNumber;

    private Integer numberOfSeats;

    private String brand;

    private String model;

    private Long mileage;

    private Car.TransmissionType transmissionType;

    private Double dailyPrice;

    private Car.Category categoryType;

    private String categoryDescription;

    // TODO: not sure about the type maybe this should only be location name or location code
    private LocationDto location;

    private Car.CarStatus status;
}
