package com.kerem.service;


import com.kerem.dto.carDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationInsertDto;
import com.kerem.dto.reservationDto.SaveReservationReturnDto;

import java.util.List;

public interface IReservationService {

    SaveReservationReturnDto saveReservation(ReservationInsertDto reservationInsertDto);
    List<RentedCarDto> getAllCurrentlyReservedCars();
    Boolean addExtraServiceToReservation(String reservationNumber, Long extraServiceId);
    Boolean returnCar(String reservationNumber);
    Boolean cancelReservation(String reservationNumber);
    Boolean deleteReservation(String reservationNumber);
    List<ReservationDto> getAllReservations();
    ReservationDto getReservationByReservationNumber(String reservationNumber);
}
