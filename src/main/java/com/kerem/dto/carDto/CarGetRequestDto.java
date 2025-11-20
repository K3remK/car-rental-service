package com.kerem.dto.carDto;

import com.kerem.dto.locationDto.LocationGetRequestDto;
import com.kerem.entities.Car;
import com.kerem.entities.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarGetRequestDto {

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

    private LocationGetRequestDto location;

    // TODO: maybe this transition from db to enum may require a special mapping
    private Car.CarStatus status;
}
