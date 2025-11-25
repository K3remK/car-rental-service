package com.kerem.mapper;

import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.customerDto.CustomerDtoIU;
import com.kerem.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer map(CustomerDtoIU customerDtoIU);
    CustomerDto map(Customer customer);
    Customer map(CustomerDto customerDto);
    void map(CustomerDtoIU source, @MappingTarget Customer destination);
    void mapGet(Customer source, @MappingTarget CustomerDto destination);

}
