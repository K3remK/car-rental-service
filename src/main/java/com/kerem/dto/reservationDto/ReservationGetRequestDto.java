package com.kerem.dto.reservationDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kerem.dto.customerDto.CustomerGetRequestDto;
import com.kerem.dto.extraServiceDto.ExtraServiceGetRequestDto;
import com.kerem.entities.Customer;
import com.kerem.entities.ExtraService;
import com.kerem.entities.Location;
import com.kerem.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
// so that when this dto is requested from customer it will not include the customer attribute since it should be null
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationGetRequestDto {

    private String reservationNumber;

    private Date creationDate;

    private Date pickUpDateAndTime;

    private Date dropOffDateAndTime;

    // TODO: maybe this transition from db to enum may require a special mapping
    private Reservation.Status status;

    private Location pickUpLocation;

    private Location dropOffLocation;

    // TODO: will be problematic since when the request comes from the customer controller since both of
    // TODO: the objects will contain customer_ssn
    // TODO: maybe make the this customer object null when the request is coming from the customer controller
    // TODO: if so set the @Json
    private CustomerGetRequestDto customer;

    private List<ExtraServiceGetRequestDto> extras;
}
