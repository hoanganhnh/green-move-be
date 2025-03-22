package com.rimmelasghar.boilerplate.springboot.service;

import com.rimmelasghar.boilerplate.springboot.dto.VehicleDto;
import com.rimmelasghar.boilerplate.springboot.dto.VehicleUpdateDto;

import java.util.List;

public interface VehicleService {
    VehicleDto createVehicle(VehicleDto vehicleDto);
    VehicleDto getVehicleById(Long id);
    VehicleDto updateVehicle(Long id, VehicleUpdateDto vehicleUpdateDto);
    void deleteVehicle(Long id);
    List<VehicleDto> getAllVehicles();
}
