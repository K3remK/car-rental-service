package com.kerem.controller;

import com.kerem.dto.reservationDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationDtoIU;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReservationController {

    ResponseEntity<ReservationDto> saveReservation(ReservationDtoIU reservationDtoIU);
    ResponseEntity<List<RentedCarDto>> getAllCurrentlyReservedCars();
    ResponseEntity<Boolean> addExtraServiceToReservation(String reservationNumber, Long extraServiceId);
    ResponseEntity<Boolean> returnCar(String reservationNumber);
    ResponseEntity<Boolean> cancelReservation(String reservationNumber);
    ResponseEntity<Boolean> deleteReservation(String reservationNumber);
}
