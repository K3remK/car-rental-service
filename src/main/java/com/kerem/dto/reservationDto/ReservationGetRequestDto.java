package com.kerem.dto.reservationDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kerem.dto.carDto.CarGetRequestDto;
import com.kerem.dto.customerDto.CustomerGetRequestDto;
import com.kerem.dto.extraServiceDto.ExtraServiceGetRequestDto;
import com.kerem.entities.Customer;
import com.kerem.entities.ExtraService;
import com.kerem.entities.Location;
import com.kerem.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
// so that when this dto is requested from customer it will not include the customer attribute since it should be null
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationGetRequestDto {

    private String reservationNumber;

    private LocalDateTime creationDate;

    private LocalDateTime pickUpDateAndTime;

    private LocalDateTime dropOffDateAndTime;

    private Reservation.Status status;

    private Location pickUpLocation;

    private Location dropOffLocation;

    private Double totalAmount;

    // TODO: will be problematic since when the request comes from the customer controller since both of
    // TODO: the objects will contain customer_ssn
    // TODO: maybe make the this customer object null when the request is coming from the customer controller
    private CustomerGetRequestDto customer;

    private CarGetRequestDto car;

    private List<ExtraServiceGetRequestDto> extras;
}
