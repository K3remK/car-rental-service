package com.kerem.dto.extraServiceDto;

import com.kerem.entities.ExtraService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtraServiceDto {

    private Long id;

    private String name;

    private String description;

    private Double totalPrice;

    private ExtraService.ExtraCategory category;
}
