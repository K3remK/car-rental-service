package com.kerem.dto.extraServiceDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kerem.entities.ExtraService;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtraServiceDto {

    private Long id;

    private String name;

    private String description;

    private Double totalPrice;

    private ExtraService.ExtraCategory category;
}
