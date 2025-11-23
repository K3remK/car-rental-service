package com.kerem.service.impl;

import com.kerem.entities.Customer;
import com.kerem.repository.CustomerRepository;
import com.kerem.service.ICustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomerBySsn(String ssn) {
        return customerRepository.findById(ssn).orElseThrow(() -> new EntityNotFoundException("Customer not found! SSN:" + ssn));
    }
}
