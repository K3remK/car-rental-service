package com.kerem.service;

import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.customerDto.CustomerDtoIU;
import com.kerem.entities.Customer;

import java.util.List;

public interface ICustomerService {

    List<CustomerDto> getAllCustomers();
    CustomerDto findCustomerBySsn(String ssn);
    CustomerDto updateCustomer(String ssn, CustomerDtoIU customerDtoIU);
    CustomerDto saveCustomer(CustomerDtoIU customerDtoIU);
    Customer getReferenceBySsn(String ssn);
}
