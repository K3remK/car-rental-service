package com.kerem.dto.locationDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDtoIU {

    @NotNull(message = "Location name cannot be empty!")
    private String locationName;

}
