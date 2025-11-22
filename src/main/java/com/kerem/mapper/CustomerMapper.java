package com.kerem.mapper;

import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.customerDto.CustomerDtoIU;
import com.kerem.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer map(CustomerDtoIU customerDtoIU);
    CustomerDto map(Customer customer);
}
