package com.rimmelasghar.boilerplate.springboot.service;

import com.rimmelasghar.boilerplate.springboot.dto.RentalDto;
import com.rimmelasghar.boilerplate.springboot.dto.RentalUpdateDto;

import java.util.List;

public interface RentalService {
    RentalDto createRental(RentalDto rentalDto);
    RentalDto getRentalById(Long id);
    List<RentalDto> getAllRentals();
    RentalDto updateRental(Long id, RentalUpdateDto rentalUpdateDto);
    void deleteRental(Long id);
}
