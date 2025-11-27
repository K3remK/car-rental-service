package com.kerem.dto.reservationDto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInsertDto {
    
    @NotNull(message = "carBarcodeNumber cannot be null!")
    private UUID carBarcodeNumber;

    @NotNull(message = "pickUpDateAndTime cannot be null!")
    private LocalDateTime pickUpDateAndTime;

    @NotNull(message = "pickUpDateAndTime cannot be null!")
    private LocalDateTime dropOffDateAndTime;

    @NotNull(message = "Customer SSN can't be null!")
    @NotEmpty(message = "Customer SSN can't be empty!")
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^\\d{11}$", message = "Ssn should be 11 character containing only digits!")
    private String ssn;

    @NotNull(message = "pickUpLocationCode cannot be null!")
    @Positive(message = "pickLocationCode must be positive")
    private Long pickUpLocationCode;

    @NotNull(message = "dropOffLocationCode cannot be null!")
    @Positive(message = "dropOffLocationCode must be positive")
    private Long dropOffLocationCode;
    private List<Long> extraServiceIds = new ArrayList<>();
}
