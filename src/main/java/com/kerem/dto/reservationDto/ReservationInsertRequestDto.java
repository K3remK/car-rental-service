package com.kerem.dto.reservationDto;

import com.kerem.dto.customerDto.CustomerInsertRequestDto;
import com.kerem.dto.extraServiceDto.ExtraServiceGetRequestDto;
import com.kerem.dto.locationDto.LocationGetRequestDto;
import com.kerem.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInsertRequestDto {

    private Date creationDate;
    private Date pickUpDateAndTime;
    private Date dropOffDateAndTime;

    private Reservation.Status status;

    // an existing location for pickup and drop-off should be selected hence the type
    private LocationGetRequestDto pickUpLocation;

    private LocationGetRequestDto dropOffLocation;

    // maybe a new customer will be inserted with the reservation hence the type
    private CustomerInsertRequestDto customer;

    // an existing extra service should be reserved hence the type
    private List<ExtraServiceGetRequestDto> extras;
}
