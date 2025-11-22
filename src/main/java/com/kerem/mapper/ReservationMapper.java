package com.kerem.mapper;

import com.kerem.dto.reservationDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationDtoIU;
import com.kerem.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    Reservation map(ReservationDtoIU reservationDtoIU);
    ReservationDto map(Reservation reservation);
    void mapReservationToRentedCarDto(Reservation source, @MappingTarget RentedCarDto destination);
}
