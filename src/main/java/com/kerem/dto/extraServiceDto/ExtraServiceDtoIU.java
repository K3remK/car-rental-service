package com.kerem.dto.extraServiceDto;

import com.kerem.entities.ExtraService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraServiceDtoIU {

    @NotNull(message = "Extra service name cannot be null or empty!")
    private String name;

    @NotNull(message = "Extra service description cannot be null or empty!")
    private String description;

    @Positive(message = "Price must be positive")
    @NotNull(message = "Extra service totalPrice cannot be null or empty!")
    private Double totalPrice;

    @NotNull(message = "Extra service category cannot be null or empty!")
    private ExtraService.ExtraCategory category;
}
