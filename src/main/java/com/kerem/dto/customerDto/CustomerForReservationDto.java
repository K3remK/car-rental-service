package com.kerem.dto.customerDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerForReservationDto {

    private String ssn;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String drivingLicenseNumber;

}
