package com.kerem.mapper;

import com.kerem.dto.carDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationInsertDto;
import com.kerem.dto.reservationDto.SaveReservationReturnDto;
import com.kerem.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    Reservation map(ReservationInsertDto reservationInsertDto);
    ReservationDto map(Reservation reservation);
    SaveReservationReturnDto mapToSaveReservationDto(Reservation reservation);
    void mapReservationToRentedCarDto(Reservation source, @MappingTarget RentedCarDto destination);
}
