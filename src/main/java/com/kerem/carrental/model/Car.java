package com.kerem.carrental.model;

public class Car {

    private String barcode; // The car's unique identifier
    private String licensePlateNumber;
    private int numberOfSeats;
    private String brand;
    private String model;
    private int mileage;
    private String transmissionType; // Automatic or Manual
    private double dailyPrice;
    private String category; // "Compact car", "Luxury car"
    private String status; // AVAILABLE, BEING_SERVICED

    // Current location of the car
    private Location location;

}
