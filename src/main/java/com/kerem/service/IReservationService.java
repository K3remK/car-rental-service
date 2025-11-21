package com.kerem.service;


import com.kerem.dto.reservationDto.RentedCarGetRequestDto;
import com.kerem.dto.reservationDto.ReservationGetRequestDto;
import com.kerem.dto.reservationDto.ReservationInsertRequestDto;
import com.kerem.entities.Car;
import com.kerem.entities.Reservation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IReservationService {

    ReservationGetRequestDto saveReservation(ReservationInsertRequestDto reservationInsertRequestDto);
    List<RentedCarGetRequestDto> getAllReservedCars();
    Boolean addExtraServiceToReservation(Long reservationNumber, Long extraServiceId);
    Boolean returnCar(Long reservationNumber);
    Boolean cancelReservation(Long reservationNumber);
    Boolean deleteReservation(Long reservationNumber);
}
