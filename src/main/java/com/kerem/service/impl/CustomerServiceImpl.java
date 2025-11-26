package com.kerem.service.impl;

import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.customerDto.CustomerDtoIU;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.entities.Customer;
import com.kerem.mapper.CustomerMapper;
import com.kerem.repository.CustomerRepository;
import com.kerem.service.ICustomerService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> getAllCustomers() {

        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            throw new EntityNotFoundException("Couldn't find any customer");
        }

        List<CustomerDto> res = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDto customerDto = customerMapper.map(customer);
            for (ReservationDto resDto : customerDto.getReservations()) {
                resDto.setCustomer(null);
                resDto.setTotalAmount(ReservationServiceImpl.calculateTotalAmount(resDto));
            }
            res.add(customerDto);
        }

        return res;
    }

    @Override
    public CustomerDto findCustomerBySsn(String ssn) {
        Customer dbCust = customerRepository.findById(ssn).orElseThrow(()
                -> new EntityNotFoundException("Customer with SSN " + ssn + " not found"));

        CustomerDto customerDto = customerMapper.map(dbCust);

        for (ReservationDto reservationDto : customerDto.getReservations()) {
            reservationDto.setCustomer(null);
            reservationDto.setTotalAmount(ReservationServiceImpl.calculateTotalAmount(reservationDto));
        }

        return customerDto;
    }

    @Transactional
    @Override
    public CustomerDto updateCustomer(String ssn, CustomerDtoIU customerDtoIU) {

        Customer dbCustomer = customerRepository.findById(customerDtoIU.getSsn()).orElseThrow(()
                -> new EntityNotFoundException("Customer not found! SSN:" + ssn));

        // update the fields of the dbCustomer
        customerMapper.map(customerDtoIU, dbCustomer);

        CustomerDto customerDto = customerMapper.map(customerRepository.save(dbCustomer));
        customerDto.setReservations(null);

        return customerDto;
    }

    @Transactional
    @Override
    public CustomerDto saveCustomer(CustomerDtoIU customerDtoIU) {

        if (customerRepository.existsById(customerDtoIU.getSsn())) {
            throw new EntityExistsException("Customer already exists! You can update instead of creating new Customer! SSN:" + customerDtoIU.getSsn());
        }

        CustomerDto customerDto = customerMapper.map(customerRepository.save(customerMapper.map(customerDtoIU)));
        customerDto.setReservations(null);

        return customerDto;
    }
}
