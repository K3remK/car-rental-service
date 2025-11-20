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

    @GetMapping(path = "/customers-list")
    @Override
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping(path = "/save")
    @Override
    public Customer saveCustomer(@RequestBody @Valid Customer newCustomer) {
        return customerService.saveCustomer(newCustomer);
    }

    @PutMapping(path = "/update/{ssn}")
    @Override
    public Customer updateCustomer(@PathVariable(name = "ssn") String ssn,
                                   @RequestBody @Valid Customer updatedCustomer) {
        return customerService.updateCustomer(ssn, updatedCustomer);
    }

    @DeleteMapping(path = "/delete/{ssn}")
    @Override
    public boolean deleteCustomer(@PathVariable(name = "ssn") String ssn) {
        return customerService.deleteCustomer(ssn);
    }
}
