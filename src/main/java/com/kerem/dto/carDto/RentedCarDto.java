package com.kerem.dto.carDto;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentedCarDto {

    // Car info
    private UUID barcode;

    private String brand;

    private String model;

    private Car.TransmissionType transmissionType;

    private Car.Category categoryType;

    // Reservation info
    private String reservationNumber;

    private LocalDateTime dropOffDateAndTime;

    private LocationDto dropOffLocation;

    private Long reservationDayCount;

    private String customerName;
}
