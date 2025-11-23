package com.kerem.controller.impl;

import com.kerem.controller.ICustomerController;
import com.kerem.entities.Customer;
import com.kerem.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentacar/api/customer")
@RequiredArgsConstructor
public class CustomerControllerImpl implements ICustomerController {

    private final ICustomerService customerService;

    @GetMapping(path = "/customers-list/{ssn}")
    @Override
    public Customer getCustomerBySsn(@PathVariable(name = "ssn") String ssn) {
        return customerService.getCustomerBySsn(ssn);
    }
}
