package com.kerem.mapper;

import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDtoIU;
import com.kerem.entities.ExtraService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExtraServiceMapper {

    ExtraServiceDto map(ExtraService service);

    ExtraService map(ExtraServiceDto serviceDto);

    @Mapping(target = "id", ignore = true)
    ExtraService map(ExtraServiceDtoIU extraServiceDtoIU);

    @Mapping(target = "id", ignore = true)
    void map(ExtraServiceDtoIU source, @MappingTarget ExtraService destination);
}
