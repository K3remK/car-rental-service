package com.kerem.dto.reservationDto;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.entities.Location;
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
public class ReservationForCustomerDto {

    private String reservationNumber;

    private LocalDateTime creationDate;

    private LocalDateTime pickUpDateAndTime;

    private LocalDateTime dropOffDateAndTime;

    private Reservation.Status status;

    private Location pickUpLocation;

    private Location dropOffLocation;

    private Double totalAmount;

    private CarDto car;

    private List<ExtraServiceDto> extras;

}
