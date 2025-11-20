package com.kerem.mapper;

import com.kerem.dto.customerDto.CustomerGetRequestDto;
import com.kerem.dto.customerDto.CustomerInsertRequestDto;
import com.kerem.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer map(CustomerInsertRequestDto customerInsertRequestDto);
    CustomerGetRequestDto map(Customer customer);
}
