package com.kerem.dto.reservationDto;

import com.kerem.dto.customerDto.CustomerInsertRequestDto;
import com.kerem.dto.extraServiceDto.ExtraServiceGetRequestDto;
import com.kerem.dto.locationDto.LocationGetRequestDto;
import com.kerem.entities.ExtraService;
import com.kerem.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInsertRequestDto {
    
    UUID carBarcodeNumber;
    LocalDateTime pickUpDateAndTime;
    LocalDateTime dropOffDateAndTime;
    String customerSsn;
    String pickUpLocationCode;
    String dropOffLocationCode;
    List<Long> extraServiceIds;
}
