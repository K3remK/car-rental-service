package com.kerem.mapper;

import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDto map(Location location);
}
