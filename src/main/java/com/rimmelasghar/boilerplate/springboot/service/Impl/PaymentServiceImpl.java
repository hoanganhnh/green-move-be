package com.rimmelasghar.boilerplate.springboot.service.Impl;

import com.rimmelasghar.boilerplate.springboot.dto.PaymentDto;
import com.rimmelasghar.boilerplate.springboot.dto.PaymentUpdateDto;
import com.rimmelasghar.boilerplate.springboot.exceptions.NotFoundException;
import com.rimmelasghar.boilerplate.springboot.mapper.PaymentMapper;
import com.rimmelasghar.boilerplate.springboot.model.Payment;
import com.rimmelasghar.boilerplate.springboot.repository.PaymentRepository;
import com.rimmelasghar.boilerplate.springboot.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        // Set created_at if not provided
        if (paymentDto.getCreated_at() == null) {
            paymentDto.setCreated_at(LocalDateTime.now());
        }
        
        // Set default status if not provided
        if (paymentDto.getStatus() == null || paymentDto.getStatus().isEmpty()) {
            paymentDto.setStatus("PENDING");
        }
        
        // Convert DTO to entity
        Payment payment = paymentMapper.toPayment(paymentDto);
        
        // Save payment
        Payment savedPayment = paymentRepository.save(payment);
        
        // Return saved payment as DTO
        return paymentMapper.toPaymentDto(savedPayment);
    }

    @Override
    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + id));
        
        return paymentMapper.toPaymentDto(payment);
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toPaymentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(paymentMapper::toPaymentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByRentalId(Long rentalId) {
        return paymentRepository.findByRentalId(rentalId).stream()
                .map(paymentMapper::toPaymentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status).stream()
                .map(paymentMapper::toPaymentDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaymentDto> getPaymentsWithFilters(Long userId, Long rentalId, String status,
                                                 String paymentMethod, BigDecimal minAmount, BigDecimal maxAmount,
                                                 LocalDateTime paymentDateFrom, LocalDateTime paymentDateTo) {
        
        Specification<Payment> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Filter by userId if provided
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }
            
            // Filter by rentalId if provided
            if (rentalId != null) {
                predicates.add(criteriaBuilder.equal(root.get("rental").get("id"), rentalId));
            }
            
            // Filter by status if provided
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            
            // Filter by paymentMethod if provided
            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod));
            }
            
            // Filter by amount range if provided
            if (minAmount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount));
            }
            if (maxAmount != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount));
            }
            
            // Filter by paymentDate range if provided
            if (paymentDateFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("paymentDate"), paymentDateFrom));
            }
            if (paymentDateTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("paymentDate"), paymentDateTo));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        
        return paymentRepository.findAll(specification).stream()
                .map(paymentMapper::toPaymentDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto updatePayment(Long id, PaymentUpdateDto paymentUpdateDto) {
        // Find payment by id
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + id));
        
        // Update payment properties
        paymentMapper.updatePaymentFromDto(paymentUpdateDto, payment);
        
        // Save updated payment
        Payment updatedPayment = paymentRepository.save(payment);
        
        // Return updated payment as DTO
        return paymentMapper.toPaymentDto(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        // Check if payment exists
        if (!paymentRepository.existsById(id)) {
            throw new NotFoundException("Payment not found with id: " + id);
        }
        
        // Delete payment
        paymentRepository.deleteById(id);
    }
}
