package com.kerem.dto.locationDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDtoIU {

    @NotNull(message = "Location name cannot be empty!")
    private String locationName;

}
