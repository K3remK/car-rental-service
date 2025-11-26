package com.kerem.dto.customerDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kerem.dto.reservationDto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {

    private String ssn;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String drivingLicenseNumber;

    List<ReservationDto> reservations = new ArrayList<>();
}
