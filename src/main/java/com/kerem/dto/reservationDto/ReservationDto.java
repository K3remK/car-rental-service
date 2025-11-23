package com.kerem.dto.reservationDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.entities.Location;
import com.kerem.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private String reservationNumber;

    private LocalDateTime pickUpDateAndTime;

    private LocalDateTime dropOffDateAndTime;

    private Location pickUpLocation;

    private Location dropOffLocation;

    private Double totalAmount;

    private String customerSsn;

    private String customerName;
}
