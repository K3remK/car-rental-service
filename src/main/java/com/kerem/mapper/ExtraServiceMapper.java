package com.kerem.mapper;

import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.entities.ExtraService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExtraServiceMapper {

    ExtraServiceMapper INSTANCE = Mappers.getMapper(ExtraServiceMapper.class);

    ExtraServiceDto map(ExtraService service);
}
