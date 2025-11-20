package com.kerem.mapper;

import com.kerem.dto.reservationDto.ReservationGetRequestDto;
import com.kerem.dto.reservationDto.ReservationInsertRequestDto;
import com.kerem.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    Reservation map(ReservationInsertRequestDto reservationInsertRequestDto);
    ReservationGetRequestDto map(Reservation reservation);

}
