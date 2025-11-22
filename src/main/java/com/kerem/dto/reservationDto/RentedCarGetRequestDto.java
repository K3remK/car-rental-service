package com.kerem.dto.reservationDto;

import com.kerem.dto.locationDto.LocationGetRequestDto;
import com.kerem.entities.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentedCarGetRequestDto {

    // Car info
    private UUID barcode;

    private String brand;

    private String model;

    private Car.TransmissionType transmissionType;

    private Car.Category categoryType;

    // Reservation info
    private Long reservationNumber;

    private LocalDateTime dropOffDateAndTime;

    private LocationGetRequestDto dropOffLocation;

    private Long reservationDayCount;

    private String customerName;
}
