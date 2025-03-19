package com.rimmelasghar.boilerplate.springboot.service;

import com.rimmelasghar.boilerplate.springboot.dto.LocationDto;

public interface LocationService {
    LocationDto createLocation(LocationDto locationDto);
    LocationDto getLocationById(Long id);
    LocationDto updateLocation(Long id, LocationDto locationDto);
    void deleteLocation(Long id);
}
