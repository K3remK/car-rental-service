package com.kerem.mapper;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.dto.locationDto.LocationDtoIU;
import com.kerem.entities.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDto map(Location location);

    @Mapping(target = "code", ignore = true)
    Location map(LocationDtoIU locationDtoIU);

    Location map(LocationDto locationDto);

    @Mapping(target = "code", ignore = true)
    void map(LocationDtoIU source, @MappingTarget Location target);
}
