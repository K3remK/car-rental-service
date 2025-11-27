package com.kerem.dto.reservationDto;

import com.kerem.dto.locationDto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveReservationReturnDto {

    private String reservationNumber;

    private LocalDateTime pickUpDateAndTime;

    private LocalDateTime dropOffDateAndTime;

    private LocationDto pickUpLocation;

    private LocationDto dropOffLocation;

    private Double totalAmount;

    private String customerSsn;

    private String customerName;
}
