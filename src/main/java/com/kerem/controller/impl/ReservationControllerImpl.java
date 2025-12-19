package com.kerem.controller.impl;

import com.kerem.controller.IReservationController;
import com.kerem.dto.carDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationInsertDto;
import com.kerem.dto.reservationDto.SaveReservationReturnDto;
import com.kerem.service.IReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rentacar/api/reservations")
@RequiredArgsConstructor
public class ReservationControllerImpl implements IReservationController {

    private final IReservationService reservationService;

    @PostMapping
    @Override
    public ResponseEntity<SaveReservationReturnDto> saveReservation(
            @RequestBody @Valid ReservationInsertDto reservationInsertDto) {
        return ResponseEntity.ok(reservationService.saveReservation(reservationInsertDto));
    }

    @GetMapping(path = "/rented-cars")
    @Override
    public ResponseEntity<List<RentedCarDto>> getAllCurrentlyReservedCars() {
        return ResponseEntity.ok(reservationService.getAllCurrentlyReservedCars());
    }

    @PutMapping(path = "/{reservationNumber}")
    @Override
    public ResponseEntity<Boolean> addExtraServiceToReservation(
            @PathVariable(name = "reservationNumber") String reservationNumber,
            @RequestParam(name = "extraServiceId") Long extraServiceId) {
        return ResponseEntity.ok(reservationService.addExtraServiceToReservation(reservationNumber, extraServiceId));
    }

    @PutMapping(path = "/{reservationNumber}/return")
    @Override
    public ResponseEntity<Boolean> returnCar(@PathVariable(name = "reservationNumber") String reservationNumber) {
        return ResponseEntity.ok(reservationService.returnCar(reservationNumber));
    }

    @PutMapping(path = "/{reservationNumber}/cancel")
    @Override
    public ResponseEntity<Boolean> cancelReservation(
            @PathVariable(name = "reservationNumber") String reservationNumber) {
        return ResponseEntity.ok().body(reservationService.cancelReservation(reservationNumber));
    }

    @DeleteMapping("/{reservationNumber}")
    @Override
    public ResponseEntity<Boolean> deleteReservation(
            @PathVariable(name = "reservationNumber") String reservationNumber) {
        return ResponseEntity.ok().body(reservationService.deleteReservation(reservationNumber));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping(path = "/{reservationNumber}")
    @Override
    public ResponseEntity<ReservationDto> getReservationByReservationNumber(
            @PathVariable(name = "reservationNumber") String reservationNumber) {
        return ResponseEntity.ok(reservationService.getReservationByReservationNumber(reservationNumber));
    }

}
