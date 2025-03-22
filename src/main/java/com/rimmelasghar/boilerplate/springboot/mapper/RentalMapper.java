package com.rimmelasghar.boilerplate.springboot.mapper;

import com.rimmelasghar.boilerplate.springboot.dto.RentalDto;
import com.rimmelasghar.boilerplate.springboot.dto.RentalUpdateDto;
import com.rimmelasghar.boilerplate.springboot.exceptions.NotFoundException;
import com.rimmelasghar.boilerplate.springboot.model.Rental;
import com.rimmelasghar.boilerplate.springboot.model.User;
import com.rimmelasghar.boilerplate.springboot.model.Vehicle;
import com.rimmelasghar.boilerplate.springboot.repository.UserRepository;
import com.rimmelasghar.boilerplate.springboot.repository.VehicleRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RentalMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Mapping(target = "user", expression = "java(getUserById(rentalDto.getUser_id()))")
    @Mapping(target = "vehicle", expression = "java(getVehicleById(rentalDto.getVehicle_id()))")
    @Mapping(source = "start_time", target = "startTime")
    @Mapping(source = "end_time", target = "endTime")
    @Mapping(source = "total_price", target = "totalPrice")
    @Mapping(source = "created_at", target = "createdAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "pickup_location", target = "pickupLocation")
    public abstract Rental toRental(RentalDto rentalDto);

    @Mapping(source = "user.id", target = "user_id")
    @Mapping(source = "vehicle.id", target = "vehicle_id")
    @Mapping(source = "startTime", target = "start_time")
    @Mapping(source = "endTime", target = "end_time")
    @Mapping(source = "totalPrice", target = "total_price")
    @Mapping(source = "createdAt", target = "created_at")
    @Mapping(source = "pickupLocation", target = "pickup_location")
    public abstract RentalDto toRentalDto(Rental rental);

    @Mapping(target = "user", expression = "java(rentalUpdateDto.getUser_id() != null ? getUserById(rentalUpdateDto.getUser_id()) : rental.getUser())")
    @Mapping(target = "vehicle", expression = "java(rentalUpdateDto.getVehicle_id() != null ? getVehicleById(rentalUpdateDto.getVehicle_id()) : rental.getVehicle())")
    @Mapping(source = "start_time", target = "startTime")
    @Mapping(source = "end_time", target = "endTime")
    @Mapping(source = "total_price", target = "totalPrice")
    @Mapping(source = "pickup_location", target = "pickupLocation")
    public abstract void updateRentalFromDto(RentalUpdateDto rentalUpdateDto, @MappingTarget Rental rental);

    protected User getUserById(Long id) {
        if (id == null) {
            return null;
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    protected Vehicle getVehicleById(Long id) {
        if (id == null) {
            return null;
        }
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + id));
    }
}
