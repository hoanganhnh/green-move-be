package com.rimmelasghar.boilerplate.springboot.mapper;

import com.rimmelasghar.boilerplate.springboot.dto.LocationDto;
import com.rimmelasghar.boilerplate.springboot.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationMapper {

    Location toLocation(LocationDto locationDto);
    
    LocationDto toLocationDto(Location location);
    
    void updateLocationFromDto(LocationDto locationDto, @MappingTarget Location location);
}
