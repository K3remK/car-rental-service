package com.kerem.controller.impl;

import com.kerem.controller.ICustomerController;
import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.customerDto.CustomerDtoIU;
import com.kerem.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentacar/api/customers")
@RequiredArgsConstructor
public class CustomerControllerImpl implements ICustomerController {

    private final ICustomerService customerService;

    @GetMapping
    @Override
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping(path = "/{ssn}")
    @Override
    public ResponseEntity<CustomerDto> getCustomerBySsn(@PathVariable(name = "ssn") String ssn) {
        return ResponseEntity.ok(customerService.findCustomerBySsn(ssn));
    }

    @PutMapping("/{ssn}")
    @Override
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable(name = "ssn") String ssn,
            @RequestBody @Valid CustomerDtoIU customerDtoIU) {
        return ResponseEntity.ok(customerService.updateCustomer(ssn, customerDtoIU));
    }

    @PostMapping
    @Override
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody @Valid CustomerDtoIU customerDtoIU) {
        return ResponseEntity.ok(customerService.saveCustomer(customerDtoIU));
    }

}
