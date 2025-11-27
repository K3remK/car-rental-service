package com.kerem.dto.carDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Car;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
