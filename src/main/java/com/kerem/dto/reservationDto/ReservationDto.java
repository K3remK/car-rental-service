package com.kerem.dto.reservationDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto {

    private String reservationNumber;

    private LocalDateTime creationDate;

    private LocalDateTime pickUpDateAndTime;

    private LocalDateTime dropOffDateAndTime;

    private Reservation.Status status;

    private Location pickUpLocation;

    private Location dropOffLocation;

    private Double totalAmount;

    private CustomerDto customer;

    private CarDto car;

    private List<ExtraServiceDto> extras;
}
