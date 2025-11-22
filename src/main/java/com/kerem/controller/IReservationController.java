package com.kerem.controller;

import com.kerem.dto.reservationDto.RentedCarGetRequestDto;
import com.kerem.dto.reservationDto.ReservationGetRequestDto;
import com.kerem.dto.reservationDto.ReservationInsertRequestDto;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public interface IReservationController {

    ResponseEntity<ReservationGetRequestDto> saveReservation(ReservationInsertRequestDto reservationInsertRequestDto);
    ResponseEntity<List<RentedCarGetRequestDto>> getAllCurrentlyReservedCars();
    ResponseEntity<Boolean> addExtraServiceToReservation(Long reservationNumber, Long extraServiceId);
    ResponseEntity<Boolean> returnCar(Long reservationNumber);
    ResponseEntity<Boolean> cancelReservation(Long reservationNumber);
    ResponseEntity<Boolean> deleteReservation(Long reservationNumber);
}
