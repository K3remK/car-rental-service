package com.kerem.dto.carDto;

import com.kerem.entities.Car;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchAvailableCarDto {

    Car.Category carCategory;

    Car.TransmissionType transmissionType;
    
    Double minPrice;
    
    Double maxPrice;

    @NotNull(message = "Pick up date is required to search available cars!")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime pickUpDate;

    @NotNull(message = "Drop off date is required to search available cars!")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime dropOffDate;
    
    Integer numberOfSeats;

    @NotNull(message = "Pick up location code is required to search available cars!")
    Long pickUpLocationCode;
    
}
