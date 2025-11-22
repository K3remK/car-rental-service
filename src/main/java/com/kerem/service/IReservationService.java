package com.kerem.service;


import com.kerem.dto.reservationDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationDtoIU;

import java.util.List;

public interface IReservationService {

    ReservationDto saveReservation(ReservationDtoIU reservationDtoIU);
    List<RentedCarDto> getAllCurrentlyReservedCars();
    Boolean addExtraServiceToReservation(Long reservationNumber, Long extraServiceId);
    Boolean returnCar(Long reservationNumber);
    Boolean cancelReservation(Long reservationNumber);
    Boolean deleteReservation(Long reservationNumber);
}
