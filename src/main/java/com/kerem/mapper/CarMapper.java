package com.kerem.mapper;

import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.reservationDto.RentedCarDto;
import com.kerem.entities.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    Car map(CarDtoIU carDtoIU);
    CarDto mapGet(Car car);
    CarDtoIU mapIU(Car car);
    void map(CarDtoIU source, @MappingTarget Car destination);
    void map(Car source, @MappingTarget RentedCarDto destination);
}
