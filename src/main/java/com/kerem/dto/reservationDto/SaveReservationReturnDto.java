package com.kerem.dto.reservationDto;

import com.kerem.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveReservationReturnDto {

    private String reservationNumber;

    private LocalDateTime pickUpDateAndTime;

    private LocalDateTime dropOffDateAndTime;

    private Location pickUpLocation;

    private Location dropOffLocation;

    private Double totalAmount;

    private String customerSsn;

    private String customerName;
}
