package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.dto.VehicleDto;
import com.rimmelasghar.boilerplate.springboot.dto.VehicleUpdateDto;
import com.rimmelasghar.boilerplate.springboot.service.VehicleService;
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

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
@Tag(name = "Vehicles", description = "API for managing vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Create a new vehicle", description = "Creates a new vehicle with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vehicle created successfully",
            content = @Content(schema = @Schema(implementation = VehicleDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Vehicle with the same license plate already exists")
    })
    @PostMapping
    public ResponseEntity<VehicleDto> createVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        VehicleDto createdVehicle = vehicleService.createVehicle(vehicleDto);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @Operation(summary = "Get vehicle by ID", description = "Returns vehicle details for the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle found",
            content = @Content(schema = @Schema(implementation = VehicleDto.class))),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @GetMapping("/{vehicle_id}")
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable("vehicle_id") Long vehicleId) {
        VehicleDto vehicleDto = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(vehicleDto);
    }

    @Operation(summary = "Update vehicle", description = "Updates an existing vehicle with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle updated successfully",
            content = @Content(schema = @Schema(implementation = VehicleDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found"),
        @ApiResponse(responseCode = "409", description = "Updated vehicle conflicts with an existing vehicle")
    })
    @PutMapping("/{vehicle_id}")
    public ResponseEntity<VehicleDto> updateVehicle(
            @PathVariable("vehicle_id") Long vehicleId,
            @Valid @RequestBody VehicleUpdateDto vehicleUpdateDto) {
        VehicleDto updatedVehicle = vehicleService.updateVehicle(vehicleId, vehicleUpdateDto);
        return ResponseEntity.ok(updatedVehicle);
    }

    @Operation(summary = "Delete vehicle", description = "Deletes the vehicle with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @DeleteMapping("/{vehicle_id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicle_id") Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.noContent().build();
    }
}
