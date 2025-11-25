package com.kerem.controller.impl;

import com.kerem.controller.ICustomerController;
import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentacar/api/customer")
@RequiredArgsConstructor
public class CustomerControllerImpl implements ICustomerController {

    private final ICustomerService customerService;

    @GetMapping(path = "/customers-list/{ssn}")
    @Override
    public ResponseEntity<CustomerDto> getCustomerBySsn(@PathVariable(name = "ssn") String ssn) {
        return ResponseEntity.ok(customerService.findCustomerBySsn(ssn));
    }
}
