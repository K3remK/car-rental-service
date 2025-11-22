package com.kerem.controller.impl;

import com.kerem.controller.IReservationController;
import com.kerem.dto.reservationDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationDtoIU;
import com.kerem.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rentacar/api/reservations")
@RequiredArgsConstructor
public class ReservationControllerImpl implements IReservationController {

    private final IReservationService reservationService;

    @PostMapping("/save")
    @Override
    public ResponseEntity<ReservationDto> saveReservation(ReservationDtoIU reservationDtoIU) {
        return null;
    }

    @GetMapping(path = "/list/currentlyRentedCars")
    @Override
    public ResponseEntity<List<RentedCarDto>> getAllCurrentlyReservedCars() {

        List<RentedCarDto> res = reservationService.getAllCurrentlyReservedCars();

        if (res.isEmpty()) {
            // TODO: throw not found exception
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping(path = "/update/addExtraService/{reservationNumber}")
    @Override
    public ResponseEntity<Boolean> addExtraServiceToReservation(@PathVariable(name = "reservationNumber") Long reservationNumber,
                                                                @RequestParam(name = "extraServiceId") Long extraServiceId) {
        return null;
    }

    @PostMapping(path = "/update/returnCar/{reservationNumber}")
    @Override
    public ResponseEntity<Boolean> returnCar(@PathVariable(name = "reservationNumber") Long reservationNumber) {
        Boolean res = reservationService.returnCar(reservationNumber);

        if (!res) {
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PostMapping(path = "/update/cancel/{reservationNumber}")
    @Override
    public ResponseEntity<Boolean> cancelReservation(@PathVariable(name = "reservationNumber") Long reservationNumber) {
        return null;
    }

    @DeleteMapping("/delete/{reservationNumber}")
    @Override
    public ResponseEntity<Boolean> deleteReservation(@PathVariable(name = "reservationNumber") Long reservationNumber) {
        return null;
    }
}
