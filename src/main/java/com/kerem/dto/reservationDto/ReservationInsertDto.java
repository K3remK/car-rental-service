package com.kerem.dto.reservationDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInsertDto {
    
    @NotNull(message = "carBarcodeNumber cannot be null!")
    private UUID carBarcodeNumber;

    @NotNull(message = "pickUpDateAndTime cannot be null!")
    private LocalDateTime pickUpDateAndTime;

    @NotNull(message = "pickUpDateAndTime cannot be null!")
    private LocalDateTime dropOffDateAndTime;

    @Size(min = 11, max = 11, message = "Ssn should be 11 character")
    private String customerSsn;

    @NotNull(message = "pickUpLocationCode cannot be null!")
    private Long pickUpLocationCode;

    @NotNull(message = "dropOffLocationCode cannot be null!")
    private Long dropOffLocationCode;
    private List<Long> extraServiceIds;
}
