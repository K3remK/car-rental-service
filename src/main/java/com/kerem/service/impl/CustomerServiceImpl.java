package com.kerem.service.impl;

import com.kerem.entities.Customer;
import com.kerem.repository.CustomerRepository;
import com.kerem.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerBySsn(String ssn) {
        return customerRepository.findById(ssn).orElse(null);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer newCustomer) {
        return null;
    }

    @Override
    public Customer updateCustomer(String ssn, Customer updatedCustomer) {
        return null;
    }

    @Override
    public boolean deleteCustomer(String ssn) {
        return false;
    }
}
