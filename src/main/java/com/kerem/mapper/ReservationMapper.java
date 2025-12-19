package com.kerem.mapper;

import com.kerem.dto.carDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationForCustomerDto;
import com.kerem.dto.reservationDto.ReservationInsertDto;
import com.kerem.dto.reservationDto.SaveReservationReturnDto;
import com.kerem.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { CarMapper.class, ExtraServiceMapper.class, LocationMapper.class })
public interface ReservationMapper {

    @Mapping(target = "reservationNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "pickUpLocation", ignore = true)
    @Mapping(target = "dropOffLocation", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "extras", ignore = true)
    Reservation map(ReservationInsertDto reservationInsertDto);

    @Mapping(target = "totalAmount", ignore = true)
    ReservationDto map(Reservation reservation);

    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(source = "customer.ssn", target = "customerSsn")
    @Mapping(target = "customerName", ignore = true)
    SaveReservationReturnDto mapToSaveReservationDto(Reservation reservation);

    @Mapping(source = "car.barcode", target = "barcode")
    @Mapping(source = "car.brand", target = "brand")
    @Mapping(source = "car.model", target = "model")
    @Mapping(source = "car.transmissionType", target = "transmissionType")
    @Mapping(source = "car.categoryType", target = "categoryType")
    @Mapping(target = "reservationDayCount", ignore = true)
    @Mapping(target = "customerName", ignore = true)
    void mapReservationToRentedCarDto(Reservation source, @MappingTarget RentedCarDto destination);

    @Mapping(target = "customer", ignore = true)
    Reservation map(ReservationForCustomerDto reservationForCustomerDto);

    @Mapping(target = "totalAmount", ignore = true)
    ReservationForCustomerDto mapToReservationForCustomerDto(Reservation reservation);
}
