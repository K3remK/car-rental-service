package com.kerem.controller;

import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.customerDto.CustomerDtoIU;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomerController {
    ResponseEntity<CustomerDto> getCustomerBySsn(String ssn);
    ResponseEntity<List<CustomerDto>> getAllCustomers();
    ResponseEntity<CustomerDto> updateCustomer(String ssn, CustomerDtoIU customerDtoIU);
    ResponseEntity<CustomerDto> saveCustomer(CustomerDtoIU customerDtoIU);
}
