package com.kerem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.IdGeneratorType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "RESERVATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @ReservationId
    private String reservationNumber;
    private LocalDateTime creationDate;
    private LocalDateTime pickUpDateAndTime;
    private LocalDateTime dropOffDateAndTime;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        ACTIVE,
        CANCELLED,
        COMPLETED
    }

    @ManyToOne
    private Location pickUpLocation;
    @ManyToOne
    private Location dropOffLocation;

    @ManyToOne
    @JoinColumn(name = "customer_ssn")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToMany
    @JoinTable(name = "REF_RESERVATION_EXTRAS",
    joinColumns = @JoinColumn(name = "reservation_number"),
    inverseJoinColumns = @JoinColumn(name = "extra_service_id"))
    private List<ExtraService> extras = new ArrayList<>();
}
