package com.rimmelasghar.boilerplate.springboot.service.Impl;

import com.rimmelasghar.boilerplate.springboot.dto.RentalDto;
import com.rimmelasghar.boilerplate.springboot.dto.RentalUpdateDto;
import com.rimmelasghar.boilerplate.springboot.exceptions.NotFoundException;
import com.rimmelasghar.boilerplate.springboot.mapper.RentalMapper;
import com.rimmelasghar.boilerplate.springboot.model.Rental;
import com.rimmelasghar.boilerplate.springboot.repository.RentalRepository;
import com.rimmelasghar.boilerplate.springboot.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @Override
    public RentalDto createRental(RentalDto rentalDto) {
        // Set created_at if not provided
        if (rentalDto.getCreated_at() == null) {
            rentalDto.setCreated_at(LocalDateTime.now());
        }
        
        // Convert DTO to entity
        Rental rental = rentalMapper.toRental(rentalDto);
        
        // Save rental
        Rental savedRental = rentalRepository.save(rental);
        
        // Return saved rental as DTO
        return rentalMapper.toRentalDto(savedRental);
    }

    @Override
    public RentalDto getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rental not found with id: " + id));
        
        return rentalMapper.toRentalDto(rental);
    }

    @Override
    public List<RentalDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toRentalDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<RentalDto> getRentalsWithFilters(Long userId, Long vehicleId, String status,
                                               LocalDateTime startTimeFrom, LocalDateTime startTimeTo,
                                               LocalDateTime endTimeFrom, LocalDateTime endTimeTo) {
        
        Specification<Rental> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Filter by userId if provided
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }
            
            // Filter by vehicleId if provided
            if (vehicleId != null) {
                predicates.add(criteriaBuilder.equal(root.get("vehicle").get("id"), vehicleId));
            }
            
            // Filter by status if provided
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            
            // Filter by startTime range if provided
            if (startTimeFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTimeFrom));
            }
            if (startTimeTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), startTimeTo));
            }
            
            // Filter by endTime range if provided
            if (endTimeFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endTime"), endTimeFrom));
            }
            if (endTimeTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endTime"), endTimeTo));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        
        return rentalRepository.findAll(specification).stream()
                .map(rentalMapper::toRentalDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentalDto updateRental(Long id, RentalUpdateDto rentalUpdateDto) {
        // Find rental by id
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rental not found with id: " + id));
        
        // Update rental properties
        rentalMapper.updateRentalFromDto(rentalUpdateDto, rental);
        
        // Save updated rental
        Rental updatedRental = rentalRepository.save(rental);
        
        // Return updated rental as DTO
        return rentalMapper.toRentalDto(updatedRental);
    }

    @Override
    public void deleteRental(Long id) {
        // Check if rental exists
        if (!rentalRepository.existsById(id)) {
            throw new NotFoundException("Rental not found with id: " + id);
        }
        
        // Delete rental
        rentalRepository.deleteById(id);
    }
}
