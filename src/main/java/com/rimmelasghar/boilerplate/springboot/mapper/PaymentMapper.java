package com.rimmelasghar.boilerplate.springboot.mapper;

import com.rimmelasghar.boilerplate.springboot.dto.PaymentDto;
import com.rimmelasghar.boilerplate.springboot.dto.PaymentUpdateDto;
import com.rimmelasghar.boilerplate.springboot.exceptions.NotFoundException;
import com.rimmelasghar.boilerplate.springboot.model.Payment;
import com.rimmelasghar.boilerplate.springboot.model.Rental;
import com.rimmelasghar.boilerplate.springboot.model.User;
import com.rimmelasghar.boilerplate.springboot.repository.RentalRepository;
import com.rimmelasghar.boilerplate.springboot.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PaymentMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Mapping(target = "user", expression = "java(getUserById(paymentDto.getUser_id()))")
    @Mapping(target = "rental", expression = "java(getRentalById(paymentDto.getRental_id()))")
    @Mapping(source = "payment_method", target = "paymentMethod")
    @Mapping(source = "payment_date", target = "paymentDate")
    @Mapping(source = "created_at", target = "createdAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    public abstract Payment toPayment(PaymentDto paymentDto);

    @Mapping(source = "user.id", target = "user_id")
    @Mapping(source = "rental.id", target = "rental_id")
    @Mapping(source = "paymentMethod", target = "payment_method")
    @Mapping(source = "paymentDate", target = "payment_date")
    @Mapping(source = "createdAt", target = "created_at")
    public abstract PaymentDto toPaymentDto(Payment payment);

    @Mapping(target = "user", expression = "java(paymentUpdateDto.getUser_id() != null ? getUserById(paymentUpdateDto.getUser_id()) : payment.getUser())")
    @Mapping(target = "rental", expression = "java(paymentUpdateDto.getRental_id() != null ? getRentalById(paymentUpdateDto.getRental_id()) : payment.getRental())")
    @Mapping(source = "payment_method", target = "paymentMethod")
    @Mapping(source = "payment_date", target = "paymentDate")
    public abstract void updatePaymentFromDto(PaymentUpdateDto paymentUpdateDto, @MappingTarget Payment payment);

    protected User getUserById(Long id) {
        if (id == null) {
            return null;
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    protected Rental getRentalById(Long id) {
        if (id == null) {
            return null;
        }
        return rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rental not found with id: " + id));
    }
}
