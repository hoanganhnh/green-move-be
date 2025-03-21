package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.dto.LocationDto;
import com.rimmelasghar.boilerplate.springboot.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
@Tag(name = "Locations", description = "API for managing locations")
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "Create a new location", description = "Creates a new location with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Location created successfully",
            content = @Content(schema = @Schema(implementation = LocationDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Location already exists")
    })
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationDto locationDto) {
        LocationDto createdLocation = locationService.createLocation(locationDto);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @Operation(summary = "Get location by ID", description = "Returns location details for the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location found",
            content = @Content(schema = @Schema(implementation = LocationDto.class))),
        @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @GetMapping("/{location_id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable("location_id") Long locationId) {
        LocationDto locationDto = locationService.getLocationById(locationId);
        return ResponseEntity.ok(locationDto);
    }

    @Operation(summary = "Update location", description = "Updates an existing location with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location updated successfully",
            content = @Content(schema = @Schema(implementation = LocationDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Location not found"),
        @ApiResponse(responseCode = "409", description = "Updated location conflicts with an existing location")
    })
    @PutMapping("/{location_id}")
    public ResponseEntity<LocationDto> updateLocation(
            @PathVariable("location_id") Long locationId,
            @Valid @RequestBody LocationDto locationDto) {
        LocationDto updatedLocation = locationService.updateLocation(locationId, locationDto);
        return ResponseEntity.ok(updatedLocation);
    }

    @Operation(summary = "Delete location", description = "Deletes the location with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @DeleteMapping("/{location_id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable("location_id") Long locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}
