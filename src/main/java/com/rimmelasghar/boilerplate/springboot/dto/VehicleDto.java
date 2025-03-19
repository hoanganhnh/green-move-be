package com.rimmelasghar.boilerplate.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "License plate is required")
    private String license_plate;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Location ID is required")
    private Long location_id;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price per day must be greater than 0")
    private BigDecimal price_per_day;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per month must be greater than 0")
    private BigDecimal price_per_month;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per year must be greater than 0")
    private BigDecimal price_per_year;
}
