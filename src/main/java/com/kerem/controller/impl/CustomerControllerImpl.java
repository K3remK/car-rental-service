package com.kerem.controller.impl;

import com.kerem.controller.ICustomerController;
import com.kerem.dto.customerDto.CustomerDto;
import com.kerem.dto.customerDto.CustomerDtoIU;
import com.kerem.dto.customerDto.CustomerForReservationDto;
import com.kerem.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping(path = "/rentacar/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "APIs for managing customers")
public class CustomerControllerImpl implements ICustomerController {

        private final ICustomerService customerService;

        @GetMapping
        @Override
        @Operation(summary = "Get all customers", description = "Retrieves a list of all registered customers")
        @ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Customers not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))

        public ResponseEntity<List<CustomerDto>> getAllCustomers() {
                return ResponseEntity.ok(customerService.getAllCustomers());
        }

        @GetMapping(path = "/{ssn}")
        @Override
        @Operation(summary = "Get customer by SSN", description = "Retrieves a specific customer by their Social Security Number")
        @ApiResponse(responseCode = "200", description = "Customer retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))

        public ResponseEntity<CustomerDto> getCustomerBySsn(
                        @Parameter(description = "Social Security Number of the customer", required = true) @PathVariable(name = "ssn") String ssn) {
                return ResponseEntity.ok(customerService.findCustomerBySsn(ssn));
        }

        @PutMapping("/{ssn}")
        @Override
        @Operation(summary = "Update customer", description = "Updates an existing customer's details")
        @ApiResponse(responseCode = "200", description = "Customer updated successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))

        public ResponseEntity<CustomerForReservationDto> updateCustomer(
                        @Parameter(description = "SSN of the customer to update", required = true) @PathVariable(name = "ssn") String ssn,
                        @Parameter(description = "Updated customer details", required = true) @RequestBody @Valid CustomerDtoIU customerDtoIU) {
                return ResponseEntity.ok(customerService.updateCustomer(ssn, customerDtoIU));
        }

        @PostMapping
        @Override
        @Operation(summary = "Create customer", description = "Registers a new customer")
        @ApiResponse(responseCode = "200", description = "Customer created successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        @ApiResponse(responseCode = "409", description = "Customer already exists", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<CustomerDto> saveCustomer(
                        @Parameter(description = "Customer details", required = true) @RequestBody @Valid CustomerDtoIU customerDtoIU) {
                return ResponseEntity.ok(customerService.saveCustomer(customerDtoIU));
        }

}
