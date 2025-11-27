package com.kerem.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "LOCATION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(length = 50, nullable = false)
    private String locationName;

}