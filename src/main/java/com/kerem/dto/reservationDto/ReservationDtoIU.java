package com.kerem.dto.reservationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDtoIU {
    
    UUID carBarcodeNumber;
    LocalDateTime pickUpDateAndTime;
    LocalDateTime dropOffDateAndTime;
    String customerSsn;
    String pickUpLocationCode;
    String dropOffLocationCode;
    List<Long> extraServiceIds;
}
