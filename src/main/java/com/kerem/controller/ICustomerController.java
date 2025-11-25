package com.kerem.controller;

import com.kerem.dto.customerDto.CustomerDto;
import org.springframework.http.ResponseEntity;

public interface ICustomerController {

    ResponseEntity<CustomerDto> getCustomerBySsn(String ssn);
}
