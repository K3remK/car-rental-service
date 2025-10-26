package com.kerem.carrental.model;

import java.util.List;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String drivingLicenseNumber;

    // A member can reserve more than one car
    List<Reservation> reservations;
}
