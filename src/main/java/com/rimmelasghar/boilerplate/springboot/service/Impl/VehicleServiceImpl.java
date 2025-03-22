package com.rimmelasghar.boilerplate.springboot.service.Impl;

import com.rimmelasghar.boilerplate.springboot.dto.VehicleDto;
import com.rimmelasghar.boilerplate.springboot.dto.VehicleUpdateDto;
import com.rimmelasghar.boilerplate.springboot.exceptions.NotFoundException;
import com.rimmelasghar.boilerplate.springboot.exceptions.ConflictException;
import com.rimmelasghar.boilerplate.springboot.mapper.VehicleMapper;
import com.rimmelasghar.boilerplate.springboot.model.Vehicle;
import com.rimmelasghar.boilerplate.springboot.repository.VehicleRepository;
import com.rimmelasghar.boilerplate.springboot.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        // Check if license plate already exists
        if (vehicleRepository.existsByLicensePlate(vehicleDto.getLicense_plate())) {
            throw new ConflictException("Vehicle with license plate " + vehicleDto.getLicense_plate() + " already exists");
        }

        // Convert DTO to entity
        Vehicle vehicle = vehicleMapper.toVehicle(vehicleDto);
        
        // Save vehicle
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        
        // Return saved vehicle as DTO
        return vehicleMapper.toVehicleDto(savedVehicle);
    }

    @Override
    public VehicleDto getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + id));
        
        return vehicleMapper.toVehicleDto(vehicle);
    }

    @Override
    public VehicleDto updateVehicle(Long id, VehicleUpdateDto vehicleUpdateDto) {
        // Find vehicle by id
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + id));
        
        // Check if license plate is being updated and already exists
        if (vehicleUpdateDto.getLicense_plate() != null && 
            !vehicleUpdateDto.getLicense_plate().equals(vehicle.getLicensePlate()) && 
            vehicleRepository.existsByLicensePlate(vehicleUpdateDto.getLicense_plate())) {
            throw new ConflictException("Vehicle with license plate " + vehicleUpdateDto.getLicense_plate() + " already exists");
        }
        
        // Update vehicle properties
        vehicleMapper.updateVehicleFromDto(vehicleUpdateDto, vehicle);
        
        // Save updated vehicle
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        
        // Return updated vehicle as DTO
        return vehicleMapper.toVehicleDto(updatedVehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        // Check if vehicle exists
        if (!vehicleRepository.existsById(id)) {
            throw new NotFoundException("Vehicle not found with id: " + id);
        }
        
        // Delete vehicle
        vehicleRepository.deleteById(id);
    }
    
    @Override
    public List<VehicleDto> getAllVehicles() {
        // Retrieve all vehicles from the repository
        List<Vehicle> vehicles = vehicleRepository.findAll();
        
        // Convert all vehicle entities to DTOs and return as a list
        return vehicles.stream()
                .map(vehicleMapper::toVehicleDto)
                .collect(Collectors.toList());
    }
}
