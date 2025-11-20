package com.kerem.mapper;

import com.kerem.dto.locationDto.LocationGetRequestDto;
import com.kerem.dto.locationDto.LocationInsertRequestDto;
import com.kerem.entities.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    Location map(LocationInsertRequestDto locationInsertRequestDto);
    LocationGetRequestDto map(Location location);
}
