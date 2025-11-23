package com.kerem.dto.reservationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    Long pickUpLocationCode;
    Long dropOffLocationCode;
    List<Long> extraServiceIds;
}
