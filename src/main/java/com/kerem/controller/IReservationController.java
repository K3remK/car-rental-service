package com.kerem.controller;

import com.kerem.dto.carDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationInsertDto;
import com.kerem.dto.reservationDto.SaveReservationReturnDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReservationController {

    ResponseEntity<SaveReservationReturnDto> saveReservation(ReservationInsertDto reservationInsertDto);
    ResponseEntity<List<RentedCarDto>> getAllCurrentlyReservedCars();
    ResponseEntity<Boolean> addExtraServiceToReservation(String reservationNumber, Long extraServiceId);
    ResponseEntity<Boolean> returnCar(String reservationNumber);
    ResponseEntity<Boolean> cancelReservation(String reservationNumber);
    ResponseEntity<Boolean> deleteReservation(String reservationNumber);

    ResponseEntity<List<ReservationDto>> getAllReservations();
    ResponseEntity<ReservationDto> getReservationByReservationNumber(String reservationNumber);
}
