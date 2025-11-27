package com.kerem.dto.customerDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDtoIU {
    @NotNull(message = "Customer SSN can't be null!")
    @Pattern(regexp = "^[1-9]\\d{10}$", message = "Ssn should be 11 character containing only digits and cannot start with 0!")
    private String ssn;

    @NotNull(message = "firstName cannot be null or empty!")
    @Pattern(regexp = "^\\p{L}{2,20}$", message = "Name must contain only letters with min length of 2 and max of 20")
    private String firstName;

    @NotNull(message = "lastName cannot be null!")
    @Pattern(regexp = "^\\p{L}{2,20}$", message = "Name must contain only letters with min length of 2 and max of 20")
    private String lastName;

    @NotNull(message = "email cannot be null!")
    @NotEmpty(message = "email cannot be empty!")
    @Email(message = "Email format is wrong!")
    private String email;

    @NotNull(message = "phoneNumber cannot be null!")
    @Pattern(regexp = "^\\+\\d{6,15}$", message = "Phone Number must match the format: a '+' followed by 6 to 15 digits (no spaces, no letters, no special characters).")
    private String phoneNumber;

    @NotNull(message = "address cannot be null!")
    @NotEmpty(message = "address cannot be empty!")
    private String address;

    @NotNull(message = "drivingLicenseNumber cannot be null!")
    @Pattern(regexp = "^\\d{6,12}$", message = "Driving license number must be 6 to 12 digit string")
    private String drivingLicenseNumber;
}
