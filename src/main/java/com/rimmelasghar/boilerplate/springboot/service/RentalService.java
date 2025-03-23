package com.rimmelasghar.boilerplate.springboot.service;

import com.rimmelasghar.boilerplate.springboot.dto.RentalDto;
import com.rimmelasghar.boilerplate.springboot.dto.RentalUpdateDto;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {
    RentalDto createRental(RentalDto rentalDto);
    RentalDto getRentalById(Long id);
    List<RentalDto> getAllRentals();
    List<RentalDto> getRentalsWithFilters(Long userId, Long vehicleId, String status, 
                                         LocalDateTime startTimeFrom, LocalDateTime startTimeTo,
                                         LocalDateTime endTimeFrom, LocalDateTime endTimeTo);
    RentalDto updateRental(Long id, RentalUpdateDto rentalUpdateDto);
    void deleteRental(Long id);
}
