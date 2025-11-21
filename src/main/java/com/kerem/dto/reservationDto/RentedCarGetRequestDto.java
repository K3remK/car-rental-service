package com.kerem.dto.reservationDto;

import com.kerem.dto.locationDto.LocationGetRequestDto;
import com.kerem.entities.Car;

import java.util.Date;

public class RentedCarGetRequestDto {

    // Car info
    private String barcode;

    private String brand;

    private String model;

    private Car.TransmissionType transmissionType;

    private Car.Category categoryType;

    // Reservation info
    private Long reservationNumber;

    private Date dropOffDateAndTime;

    private LocationGetRequestDto location;

    private Integer reservationDayCount;

    private String customerName;
}
