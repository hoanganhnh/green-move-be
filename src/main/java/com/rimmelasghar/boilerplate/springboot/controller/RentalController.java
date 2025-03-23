package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.dto.RentalDto;
import com.rimmelasghar.boilerplate.springboot.dto.RentalUpdateDto;
import com.rimmelasghar.boilerplate.springboot.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rentals")
@Tag(name = "Rentals", description = "API for managing rentals")
public class RentalController {

    private final RentalService rentalService;

    @Operation(summary = "Create a new rental", description = "Creates a new rental with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rental created successfully",
            content = @Content(schema = @Schema(implementation = RentalDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User or Vehicle not found")
    })
    @PostMapping
    public ResponseEntity<RentalDto> createRental(@Valid @RequestBody RentalDto rentalDto) {
        RentalDto createdRental = rentalService.createRental(rentalDto);
        return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
    }

    @Operation(summary = "Get rental by ID", description = "Returns rental details for the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental found",
            content = @Content(schema = @Schema(implementation = RentalDto.class))),
        @ApiResponse(responseCode = "404", description = "Rental not found")
    })
    @GetMapping("/{rental_id}")
    public ResponseEntity<RentalDto> getRental(@PathVariable("rental_id") Long rentalId) {
        RentalDto rentalDto = rentalService.getRentalById(rentalId);
        return ResponseEntity.ok(rentalDto);
    }

    @Operation(summary = "Get all rentals", description = "Returns a list of all rentals with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of rentals retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<RentalDto>> getAllRentals(
            @RequestParam(required = false) Long user_id,
            @RequestParam(required = false) Long vehicle_id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start_time_from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start_time_to,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end_time_from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end_time_to) {
        
        // If no filters are provided, return all rentals
        if (user_id == null && vehicle_id == null && status == null && 
            start_time_from == null && start_time_to == null && 
            end_time_from == null && end_time_to == null) {
            List<RentalDto> rentals = rentalService.getAllRentals();
            return ResponseEntity.ok(rentals);
        }
        
        // Otherwise, apply filters
        List<RentalDto> filteredRentals = rentalService.getRentalsWithFilters(
                user_id, vehicle_id, status, 
                start_time_from, start_time_to, 
                end_time_from, end_time_to);
        return ResponseEntity.ok(filteredRentals);
    }

    @Operation(summary = "Update rental", description = "Updates an existing rental with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental updated successfully",
            content = @Content(schema = @Schema(implementation = RentalDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Rental, User, or Vehicle not found")
    })
    @PutMapping("/{rental_id}")
    public ResponseEntity<RentalDto> updateRental(
            @PathVariable("rental_id") Long rentalId,
            @Valid @RequestBody RentalUpdateDto rentalUpdateDto) {
        RentalDto updatedRental = rentalService.updateRental(rentalId, rentalUpdateDto);
        return ResponseEntity.ok(updatedRental);
    }

    @Operation(summary = "Delete rental", description = "Deletes the rental with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Rental deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Rental not found")
    })
    @DeleteMapping("/{rental_id}")
    public ResponseEntity<Void> deleteRental(@PathVariable("rental_id") Long rentalId) {
        rentalService.deleteRental(rentalId);
        return ResponseEntity.noContent().build();
    }
}
