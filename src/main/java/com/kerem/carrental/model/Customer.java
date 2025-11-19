package com.kerem.carrental.model;

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

    @Id
    @Size(min = 11, max = 11)
    private String ssn;

    @Column(length = 20, nullable = false)
    private String firstName;
    @Column(length = 20, nullable = false)
    private String lastName;

    @Email(message = "Email format is wrong!")
    private String email;
    @Column(length = 15, nullable = false)
    @Size(min = 6, max = 15)
    private String phoneNumber;
    private String address;
    private String drivingLicenseNumber;

    @OneToMany
    List<Reservation> reservations;
}
