package com.kerem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "CAR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    // TODO: learn about cascade types and uni and bi-directional mappings

    @Id
    @UuidGenerator
    private UUID barcode;

    private String licensePlateNumber;

    private Integer numberOfSeats;

    private String brand;

    private String model;

    private Long mileage;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

    private Double dailyPrice;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Category categoryType;

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

    public enum TransmissionType {
        AUTOMATIC,
        MANUAL
    }

    public enum Category {
        COMPACT,
        MID_SIZE,
        FULL_SIZE,
        STATION_WAGON,
        LUXURY,
        VAN,
        SUV,
        TRUCK,
        ELECTRIC,
        HYBRID
    }

    public enum CarStatus {
        IN_SERVICE,
        OUT_OF_SERVICE
    }
}
