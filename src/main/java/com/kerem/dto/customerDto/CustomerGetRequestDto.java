package com.kerem.dto.customerDto;

import com.kerem.dto.reservationDto.ReservationGetRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGetRequestDto {

    private String ssn;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String drivingLicenseNumber;

    List<ReservationGetRequestDto> reservations;
}
