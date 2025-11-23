package com.kerem.service;

import com.kerem.entities.Customer;

import java.util.List;

public interface ICustomerService {

    Customer getCustomerBySsn(String ssn);

}
