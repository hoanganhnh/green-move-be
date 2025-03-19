package com.rimmelasghar.boilerplate.springboot.service.Impl;

import com.rimmelasghar.boilerplate.springboot.dto.LocationDto;
import com.rimmelasghar.boilerplate.springboot.exceptions.ConflictException;
import com.rimmelasghar.boilerplate.springboot.exceptions.NotFoundException;
import com.rimmelasghar.boilerplate.springboot.mapper.LocationMapper;
import com.rimmelasghar.boilerplate.springboot.model.Location;
import com.rimmelasghar.boilerplate.springboot.repository.LocationRepository;
import com.rimmelasghar.boilerplate.springboot.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public LocationDto createLocation(LocationDto locationDto) {
        // Check if location with same name and address already exists
        if (locationRepository.existsByNameAndAddress(locationDto.getName(), locationDto.getAddress())) {
            throw new ConflictException("Location with name '" + locationDto.getName() + 
                                      "' and address '" + locationDto.getAddress() + "' already exists");
        }

        // Convert DTO to entity
        Location location = locationMapper.toLocation(locationDto);
        
        // Save location
        Location savedLocation = locationRepository.save(location);
        
        // Return saved location as DTO
        return locationMapper.toLocationDto(savedLocation);
    }

    @Override
    public LocationDto getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Location not found with id: " + id));
        
        return locationMapper.toLocationDto(location);
    }

    @Override
    public LocationDto updateLocation(Long id, LocationDto locationDto) {
        // Find location by id
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Location not found with id: " + id));
        
        // Check if updated location with same name and address already exists (excluding current location)
        if (!location.getName().equals(locationDto.getName()) || 
            !location.getAddress().equals(locationDto.getAddress())) {
            if (locationRepository.existsByNameAndAddress(locationDto.getName(), locationDto.getAddress())) {
                throw new ConflictException("Location with name '" + locationDto.getName() + 
                                          "' and address '" + locationDto.getAddress() + "' already exists");
            }
        }
        
        // Update location properties
        locationMapper.updateLocationFromDto(locationDto, location);
        
        // Save updated location
        Location updatedLocation = locationRepository.save(location);
        
        // Return updated location as DTO
        return locationMapper.toLocationDto(updatedLocation);
    }

    @Override
    public void deleteLocation(Long id) {
        // Check if location exists
        if (!locationRepository.existsById(id)) {
            throw new NotFoundException("Location not found with id: " + id);
        }
        
        // Delete location
        locationRepository.deleteById(id);
    }
}
