package com.kerem.service.impl;

import com.kerem.dto.reservationDto.RentedCarGetRequestDto;
import com.kerem.dto.reservationDto.ReservationGetRequestDto;
import com.kerem.dto.reservationDto.ReservationInsertRequestDto;
import com.kerem.entities.Car;
import com.kerem.service.IReservationService;

import java.util.List;

public class ReservationServiceImpl implements IReservationService {
    @Override
    public ReservationGetRequestDto saveReservation(ReservationInsertRequestDto reservationInsertRequestDto) {
        return null;
    }

    @Override
    public List<RentedCarGetRequestDto> getAllReservedCars() {
        return List.of();
    }

    @Override
    public Boolean addExtraServiceToReservation(Long reservationNumber, Long extraServiceId) {
        return null;
    }

    @Override
    public Boolean returnCar(Long reservationNumber) {
        return null;
    }

    @Override
    public Boolean cancelReservation(Long reservationNumber) {
        return null;
    }

    @Override
    public Boolean deleteReservation(Long reservationNumber) {
        //TODO: do not use Casecade.DELETE
        return null;
    }
}
