package com.rimmelasghar.boilerplate.springboot.mapper;

import com.rimmelasghar.boilerplate.springboot.dto.VehicleDto;
import com.rimmelasghar.boilerplate.springboot.dto.VehicleUpdateDto;
import com.rimmelasghar.boilerplate.springboot.model.Location;
import com.rimmelasghar.boilerplate.springboot.model.Vehicle;
import com.rimmelasghar.boilerplate.springboot.repository.LocationRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {LocationRepository.class})
public abstract class VehicleMapper {

    @Autowired
    private LocationRepository locationRepository;

    @Mapping(source = "license_plate", target = "licensePlate")
    @Mapping(target = "location", expression = "java(getLocationById(vehicleDto.getLocation_id()))")
    @Mapping(source = "price_per_day", target = "pricePerDay")
    @Mapping(source = "price_per_month", target = "pricePerMonth")
    @Mapping(source = "price_per_year", target = "pricePerYear")
    @Mapping(source = "image", target = "image")
    public abstract Vehicle toVehicle(VehicleDto vehicleDto);

    @Mapping(source = "licensePlate", target = "license_plate")
    @Mapping(source = "location.id", target = "location_id")
    @Mapping(source = "pricePerDay", target = "price_per_day")
    @Mapping(source = "pricePerMonth", target = "price_per_month")
    @Mapping(source = "pricePerYear", target = "price_per_year")
    @Mapping(source = "image", target = "image")
    public abstract VehicleDto toVehicleDto(Vehicle vehicle);

    @Mapping(source = "license_plate", target = "licensePlate")
    @Mapping(target = "location", expression = "java(vehicleUpdateDto.getLocation_id() != null ? getLocationById(vehicleUpdateDto.getLocation_id()) : vehicle.getLocation())")
    @Mapping(source = "price_per_day", target = "pricePerDay")
    @Mapping(source = "price_per_month", target = "pricePerMonth")
    @Mapping(source = "price_per_year", target = "pricePerYear")
    @Mapping(source = "image", target = "image")
    public abstract void updateVehicleFromDto(VehicleUpdateDto vehicleUpdateDto, @MappingTarget Vehicle vehicle);
    
    protected Location getLocationById(Long id) {
        if (id == null) {
            return null;
        }
        return locationRepository.findById(id)
                .orElseThrow(() -> new com.rimmelasghar.boilerplate.springboot.exceptions.NotFoundException("Location not found with id: " + id));
    }
}
