package com.kerem.dto.extraServiceDto;

import com.kerem.entities.ExtraService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtraServiceGetRequestDto {

    private Long id;

    private String name;

    private String description;

    private Double totalPrice;

    // TODO: maybe this transition from db to enum may require a special mapping
    private ExtraService.ExtraCategory category;
}
