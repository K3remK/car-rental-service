package com.kerem.dto.reservationDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.customerDto.CustomerForReservationDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto {

    private String reservationNumber;

    private LocalDateTime creationDate;

    private LocalDateTime pickUpDateAndTime;

    private LocalDateTime dropOffDateAndTime;

    private Reservation.Status status;

    private LocationDto pickUpLocation;

    private LocationDto dropOffLocation;

    private Double totalAmount;

    private CustomerForReservationDto customer;

    private CarDto car;

    private List<ExtraServiceDto> extras;
}
