package com.kerem;

import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.customerDto.CustomerDtoIU;
import com.kerem.entities.Customer;
import com.kerem.repository.CustomerRepository;
import com.kerem.service.ICustomerService;
import com.kerem.starter.CarRentalServiceApplication;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = CarRentalServiceApplication.class)
@ActiveProfiles("test")
@Transactional
public class CustomerServiceTest {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        // Arrange
        CustomerDtoIU dto = new CustomerDtoIU();
        dto.setSsn("12345678901");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john@example.com");
        dto.setPhoneNumber("5551234567");
        dto.setDrivingLicenseNumber("DL12345");
        dto.setAddress("123 Main St");

        // Act
        CustomerDto saved = customerService.saveCustomer(dto);

        // Assert
        Assertions.assertEquals("12345678901", saved.getSsn());
        Assertions.assertEquals("John", saved.getFirstName());
    }

    @Test
    void testSaveCustomer_AlreadyExists() {
        // Arrange
        Customer c = new Customer();
        c.setSsn("11111111111");
        c.setFirstName("Jane");
        c.setLastName("Doe");
        c.setPhoneNumber("5559876543");
        c.setDrivingLicenseNumber("DL99999");
        customerRepository.save(c);

        CustomerDtoIU dto = new CustomerDtoIU();
        dto.setSsn("11111111111"); // Same SSN

        // Act & Assert
        Assertions.assertThrows(EntityExistsException.class, () -> customerService.saveCustomer(dto));
    }

    @Test
    void testFindCustomerBySsn() {
        // Arrange
        Customer c = new Customer();
        c.setSsn("22222222222");
        c.setFirstName("Test");
        c.setLastName("User");
        c.setPhoneNumber("5550000000");
        c.setDrivingLicenseNumber("DL00000");
        customerRepository.save(c);

        // Act
        CustomerDto found = customerService.findCustomerBySsn("22222222222");

        // Assert
        Assertions.assertEquals("Test", found.getFirstName());
    }

    @Test
    void testUpdateCustomer() {
        // Arrange
        Customer c = new Customer();
        c.setSsn("33333333333");
        c.setFirstName("OldName");
        c.setLastName("OldLast");
        c.setPhoneNumber("5551111111");
        c.setDrivingLicenseNumber("DL11111");
        customerRepository.save(c);

        CustomerDtoIU updateDto = new CustomerDtoIU();
        updateDto.setSsn("33333333333");
        updateDto.setFirstName("NewName");
        updateDto.setLastName("NewLast");
        updateDto.setPhoneNumber("5551111111"); // Keeping unique fields to avoid collision
        updateDto.setDrivingLicenseNumber("DL11111");

        // Act
        CustomerDto updated = customerService.updateCustomer("33333333333", updateDto);

        // Assert
        Assertions.assertEquals("NewName", updated.getFirstName());
        Assertions.assertEquals("NewLast", updated.getLastName());
    }

    @Test
    void testGetAllCustomers() {
        // Arrange
        Customer c = new Customer();
        c.setSsn("44444444444");
        c.setFirstName("Alice");
        c.setLastName("Smith");
        c.setPhoneNumber("5552222222");
        c.setDrivingLicenseNumber("DL22222");
        customerRepository.save(c);

        // Act
        List<CustomerDto> customers = customerService.getAllCustomers();

        // Assert
        Assertions.assertFalse(customers.isEmpty());
    }
}