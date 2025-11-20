package com.kerem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "CUSTOMER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    // TODO: move the validation annotations to the insert and update dto objects

    @Id
    private String ssn;

    @Column(length = 20, nullable = false)
    private String firstName;
    @Column(length = 20, nullable = false)
    private String lastName;

    private String email;
    @Column(length = 15, nullable = false, unique = true)

    private String phoneNumber;
    private String address;
    @Column(length = 20, nullable = false, unique = true)
    private String drivingLicenseNumber;

    // TODO: this will create issues when reservations requested from CustomerController since both customer object itself and the reservations they made
    // TODO: will contain customer_ssn
    // SOLUTION: make the customer_ssn null and don't return it in the ReservationDTO for CustomerController
    @OneToMany(mappedBy = "customer")
    List<Reservation> reservations;
}
