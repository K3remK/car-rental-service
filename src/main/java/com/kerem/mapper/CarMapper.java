package com.kerem.mapper;

import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.SearchAvailableCarDto;
import com.kerem.dto.carDto.SearchCarWithParamsDto;
import com.kerem.dto.carDto.RentedCarDto;
import com.kerem.entities.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(target = "barcode", ignore = true)
    @Mapping(target = "location", ignore = true)
    Car map(CarDtoIU carDtoIU);

    CarDto mapGet(Car car);

    Car map(CarDto carDto);

    @Mapping(source = "location.code", target = "locationCode")
    CarDtoIU mapIU(Car car);

    @Mapping(target = "barcode", ignore = true)
    @Mapping(target = "location", ignore = true)
    void map(CarDtoIU source, @MappingTarget Car destination);

    @Mapping(target = "reservationNumber", ignore = true)
    @Mapping(target = "dropOffDateAndTime", ignore = true)
    @Mapping(target = "dropOffLocation", ignore = true)
    @Mapping(target = "reservationDayCount", ignore = true)
    @Mapping(target = "customerName", ignore = true)
    void map(Car source, @MappingTarget RentedCarDto destination);

    @Mapping(target = "reservationNumber", ignore = true)
    @Mapping(target = "dropOffDateAndTime", ignore = true)
    @Mapping(target = "dropOffLocation", ignore = true)
    @Mapping(target = "reservationDayCount", ignore = true)
    @Mapping(target = "customerName", ignore = true)
    void map(CarDto source, @MappingTarget RentedCarDto destination);

    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "licensePlate", ignore = true)
    @Mapping(target = "maxMileage", ignore = true)
    @Mapping(target = "model", ignore = true)
    SearchCarWithParamsDto map(SearchAvailableCarDto searchAvailableCarDto);
}
