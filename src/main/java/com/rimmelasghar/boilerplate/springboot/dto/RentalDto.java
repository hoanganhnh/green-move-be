package com.rimmelasghar.boilerplate.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {

    private Long id;

    @NotNull(message = "User ID is required")
    private Long user_id;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicle_id;

    @NotNull(message = "Start time is required")
    private LocalDateTime start_time;

    @NotNull(message = "End time is required")
    private LocalDateTime end_time;

    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    private BigDecimal total_price;

    @NotNull(message = "Status is required")
    private String status;

    private LocalDateTime created_at;
    
    private String pickup_location;
}
