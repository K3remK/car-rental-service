package com.kerem.dto.customerDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInsertRequestDto {

    // TODO: maybe insert and update dto's can be merged as one object

    @NotNull(message = "Customer SSN can't be null!")
    @NotEmpty(message = "Customer SSN can't be empty!")
    @Size(min = 11, max = 11)
    private String ssn;

    @Size(min = 2, max = 20)
    private String firstName;

    @Size(min = 2, max = 20)
    private String lastName;

    @Email(message = "Email format is wrong!")
    private String email;

    @Size(min = 6, max = 15)
    private String phoneNumber;

    private String address;

    @Size(min = 6, max = 12)
    private String drivingLicenseNumber;
}
