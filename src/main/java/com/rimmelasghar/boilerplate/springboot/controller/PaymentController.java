package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.dto.PaymentDto;
import com.rimmelasghar.boilerplate.springboot.dto.PaymentUpdateDto;
import com.rimmelasghar.boilerplate.springboot.service.PaymentService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Tag(name = "Payments", description = "API for managing payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Create a new payment", description = "Creates a new payment with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Payment created successfully",
            content = @Content(schema = @Schema(implementation = PaymentDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User or Rental not found")
    })
    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentDto paymentDto) {
        PaymentDto createdPayment = paymentService.createPayment(paymentDto);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @Operation(summary = "Get payment by ID", description = "Returns payment details for the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment found",
            content = @Content(schema = @Schema(implementation = PaymentDto.class))),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/{payment_id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable("payment_id") Long paymentId) {
        PaymentDto paymentDto = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(paymentDto);
    }

    @Operation(summary = "Get all payments", description = "Returns a list of all payments with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments(
            @RequestParam(required = false) Long user_id,
            @RequestParam(required = false) Long rental_id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String payment_method,
            @RequestParam(required = false) BigDecimal min_amount,
            @RequestParam(required = false) BigDecimal max_amount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime payment_date_from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime payment_date_to) {
        
        // If no filters are provided, return all payments
        if (user_id == null && rental_id == null && status == null && payment_method == null && 
            min_amount == null && max_amount == null && 
            payment_date_from == null && payment_date_to == null) {
            List<PaymentDto> payments = paymentService.getAllPayments();
            return ResponseEntity.ok(payments);
        }
        
        // Otherwise, apply filters
        List<PaymentDto> filteredPayments = paymentService.getPaymentsWithFilters(
                user_id, rental_id, status, payment_method, 
                min_amount, max_amount, 
                payment_date_from, payment_date_to);
        return ResponseEntity.ok(filteredPayments);
    }

    @Operation(summary = "Get payments by user ID", description = "Returns a list of payments for the specified user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully")
    })
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByUserId(@PathVariable("user_id") Long userId) {
        List<PaymentDto> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get payments by rental ID", description = "Returns a list of payments for the specified rental")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully")
    })
    @GetMapping("/rental/{rental_id}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByRentalId(@PathVariable("rental_id") Long rentalId) {
        List<PaymentDto> payments = paymentService.getPaymentsByRentalId(rentalId);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get payments by status", description = "Returns a list of payments with the specified status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of payments retrieved successfully")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByStatus(@PathVariable String status) {
        List<PaymentDto> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Update payment", description = "Updates an existing payment with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment updated successfully",
            content = @Content(schema = @Schema(implementation = PaymentDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{payment_id}")
    public ResponseEntity<PaymentDto> updatePayment(
            @PathVariable("payment_id") Long paymentId,
            @Valid @RequestBody PaymentUpdateDto paymentUpdateDto) {
        PaymentDto updatedPayment = paymentService.updatePayment(paymentId, paymentUpdateDto);
        return ResponseEntity.ok(updatedPayment);
    }

    @Operation(summary = "Delete payment", description = "Deletes the payment with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/{payment_id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("payment_id") Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }
}
