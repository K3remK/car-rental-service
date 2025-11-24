package com.kerem.entities;

import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "customer")
    List<Reservation> reservations;
}
