package com.kerem.service;

import com.kerem.entities.Customer;

import java.util.List;

public interface ICustomerService {

    Customer getCustomerBySsn(String ssn);
    List<Customer> getAllCustomers();
    Customer saveCustomer(Customer newCustomer);
    Customer updateCustomer(String ssn, Customer updatedCustomer);
    boolean deleteCustomer(String ssn);

}
