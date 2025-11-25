package com.kerem.mapper;

import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDtoIU;
import com.kerem.entities.ExtraService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExtraServiceMapper {

    ExtraServiceDto map(ExtraService service);
    ExtraService map(ExtraServiceDto serviceDto);
    ExtraService map(ExtraServiceDtoIU extraServiceDtoIU);
    void map(ExtraServiceDtoIU source, @MappingTarget ExtraService destination);
}
