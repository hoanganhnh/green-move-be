package com.rimmelasghar.boilerplate.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Long id;

    @NotNull(message = "Rental ID is required")
    private Long rental_id;

    @NotNull(message = "User ID is required")
    private Long user_id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Payment method is required")
    private String payment_method;

    @NotNull(message = "Payment date is required")
    private LocalDateTime payment_date;

    private String status;

    private LocalDateTime created_at;
}
