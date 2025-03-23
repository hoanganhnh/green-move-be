package com.rimmelasghar.boilerplate.springboot.service;

import com.rimmelasghar.boilerplate.springboot.dto.PaymentDto;
import com.rimmelasghar.boilerplate.springboot.dto.PaymentUpdateDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    PaymentDto getPaymentById(Long id);
    List<PaymentDto> getAllPayments();
    List<PaymentDto> getPaymentsByUserId(Long userId);
    List<PaymentDto> getPaymentsByRentalId(Long rentalId);
    List<PaymentDto> getPaymentsByStatus(String status);
    List<PaymentDto> getPaymentsWithFilters(Long userId, Long rentalId, String status,
                                           String paymentMethod, BigDecimal minAmount, BigDecimal maxAmount,
                                           LocalDateTime paymentDateFrom, LocalDateTime paymentDateTo);
    PaymentDto updatePayment(Long id, PaymentUpdateDto paymentUpdateDto);
    void deletePayment(Long id);
}
