package com.rimmelasghar.boilerplate.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleUpdateDto {

    private String name;
    private String brand;
    private String type;
    private String license_plate;
    private String status;
    private Long location_id;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per day must be greater than 0")
    private BigDecimal price_per_day;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per month must be greater than 0")
    private BigDecimal price_per_month;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per year must be greater than 0")
    private BigDecimal price_per_year;
}
