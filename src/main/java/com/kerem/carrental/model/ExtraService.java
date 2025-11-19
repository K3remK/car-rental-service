package com.kerem.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EXTRA_SERVICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(length = 10, nullable = false)
    private Double totalPrice;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ExtraCategory category;
    public enum ExtraCategory {
        DRIVER,
        CHILD,
        TECHNOLOGY,
        WINTER,
        CONVENIENCE,
        INSURANCE,
        SPECIAL
    }

}
