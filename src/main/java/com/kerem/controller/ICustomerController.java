package com.kerem.controller;

import com.kerem.entities.Customer;

import java.util.List;

public interface ICustomerController {

    Customer getCustomerBySsn(String ssn);
}
