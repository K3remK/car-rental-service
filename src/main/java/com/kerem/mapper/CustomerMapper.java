package com.kerem.mapper;

import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.customerDto.CustomerDtoIU;
import com.kerem.dto.customerDto.CustomerForReservationDto;
import com.kerem.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { ReservationMapper.class })
public interface CustomerMapper {
    @Mapping(target = "reservations", ignore = true)
    Customer map(CustomerDtoIU customerDtoIU);

    CustomerDto map(Customer customer);

    CustomerForReservationDto mapToReservationDto(Customer customer);

    Customer map(CustomerDto customerDto);

    @Mapping(target = "reservations", ignore = true)
    void map(CustomerDtoIU source, @MappingTarget Customer destination);

    void mapGet(Customer source, @MappingTarget CustomerDto destination);

}
