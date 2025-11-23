package com.kerem.controller.impl;

import com.kerem.controller.ICustomerController;
import com.kerem.entities.Customer;
import com.kerem.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentacar/api/customer")
public class CustomerControllerImpl implements ICustomerController {

    // TODO: change the types with DTOs this version is temporary

    private final ICustomerService customerService;

    public CustomerControllerImpl(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/customers-list/{ssn}")
    @Override
    public Customer getCustomerBySsn(@PathVariable(name = "ssn") String ssn) {
        return customerService.getCustomerBySsn(ssn);
    }
}
