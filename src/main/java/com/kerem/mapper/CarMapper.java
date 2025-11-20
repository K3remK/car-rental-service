package com.kerem.mapper;

import com.kerem.dto.carDto.CarGetRequestDto;
import com.kerem.dto.carDto.CarInsertRequestDto;
import com.kerem.entities.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    Car map(CarInsertRequestDto carInsertRequestDto);
    CarGetRequestDto map(Car car);
}
