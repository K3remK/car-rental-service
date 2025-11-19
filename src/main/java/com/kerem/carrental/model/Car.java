package com.kerem.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CAR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String barcode;

    private String licensePlateNumber;

    private Integer numberOfSeats;

    private String brand;

    private String model;

    private Long mileage;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;
    public enum TransmissionType {
        AUTOMATIC,
        MANUAL
    }

    private Double dailyPrice;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Category categoryType;
    public enum Category {
        COMPACT,
        MIDSIZE,
        FULLSIZE,
        STATIONWAGON,
        LUXURY,
        VAN,
        SUV,
        TRUCK,
        ELECTRIC,
        HYBRID
    }

    @Column(length = 50, nullable = false)
    private String categoryDescription;

    // ManyToOne relations with the Locations table
    // as a car can only be in one location
    // but a location could have multiple cars
    @ManyToOne
    private Location location;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    public enum CarStatus {
        ACTIVE,
        IN_SERVICE,
        OUT_OF_SERVICE
    }

}
