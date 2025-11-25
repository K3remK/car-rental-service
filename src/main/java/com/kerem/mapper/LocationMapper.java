package com.kerem.mapper;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.dto.locationDto.LocationDtoIU;
import com.kerem.entities.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDto map(Location location);
    Location map(LocationDtoIU locationDtoIU);
    Location map(LocationDto locationDto);
    void map(LocationDtoIU source, @MappingTarget Location target);
}
