package com.kerem;

import com.kerem.entities.Customer;
import com.kerem.repository.CustomerRepository;
import com.kerem.service.impl.CustomerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("Get customer by SSN - Success")
    void testGetCustomerBySsn_Success() {
        Customer customer = new Customer();
        customer.setSsn("12345");

        when(customerRepository.findById(customer.getSsn())).thenReturn(Optional.of(customer));

        Customer result =  customerService.getCustomerBySsn(customer.getSsn());

        assertNotNull(result);
        assertEquals(customer.getSsn(), result.getSsn());
        verify(customerRepository).findById(customer.getSsn());
    }

    @Test
    @DisplayName("Get customer by SSN - Throws Not Found Exception")
    void testGetCustomerBySsn_NotFound() {
        String nonExistentSsn = "12345";

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                customerService.getCustomerBySsn(nonExistentSsn)
        );

        assertEquals("Customer not found! SSN:" + nonExistentSsn, exception.getMessage());
        verify(customerRepository).findById(nonExistentSsn);
    }
}
