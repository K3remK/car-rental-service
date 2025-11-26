package com.kerem.dto.carDto;

import com.kerem.entities.Car;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoIU {

    @NotNull(message = "licensePlateNumber cannot be null or empty")
    @Pattern(
            regexp = "^[0-9]{2,3} \\p{L}{1,3} [0-9]{2,3}$",
            message = "License plate must be 2–3 digits, 1–3 letters, then 2–3 digits (e.g., 34 ABC 123)"
    )
    private String licensePlateNumber;

    @NotNull(message = "numberOfSeats cannot be null")
    @Min(value = 2, message = "A car must have at least 2 seats")
    @Max(value = 9, message = "A car cannot have more than 9 seats")
    private Integer numberOfSeats;

    @NotNull(message = "brand cannot be null or empty")
    @NotNull(message = "brand cannot be null or empty")
    @Size(min = 2, max = 30, message = "Brand must be 2–30 characters long")
    private String brand;

    @NotNull(message = "model cannot be null or empty")
    @Size(min = 1, max = 30, message = "Model must be 1–30 characters long")
    private String model;

    @Positive(message = "Mileage cannot be negative")
    @NotNull(message = "mileage cannot be null or empty")
    private Long mileage;

    @NotNull(message = "transmissionType cannot be null or empty")
    private Car.TransmissionType transmissionType;

    @Positive(message = "Price must be positive")
    @NotNull(message = "dailyPrice cannot be null or empty")
    private Double dailyPrice;

    @NotNull(message = "categoryType cannot be null or empty")
    private Car.Category categoryType;

    @NotNull(message = "categoryDescription cannot be null")
    @Size(min = 5, max = 200, message = "Description must be 5–200 characters long")
    private String categoryDescription;

    @Positive
    @NotNull(message = "Location code cannot be null")
    private Long locationCode;

    @NotNull(message = "status cannot be null or empty")
    private Car.CarStatus status;
}
