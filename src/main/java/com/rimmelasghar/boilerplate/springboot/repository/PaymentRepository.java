package com.rimmelasghar.boilerplate.springboot.repository;

import com.rimmelasghar.boilerplate.springboot.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
    List<Payment> findByUserId(Long userId);
    List<Payment> findByRentalId(Long rentalId);
    List<Payment> findByStatus(String status);
}
