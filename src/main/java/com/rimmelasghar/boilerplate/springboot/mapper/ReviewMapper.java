package com.rimmelasghar.boilerplate.springboot.mapper;

import com.rimmelasghar.boilerplate.springboot.dto.ReviewDto;
import com.rimmelasghar.boilerplate.springboot.dto.ReviewUpdateDto;
import com.rimmelasghar.boilerplate.springboot.exceptions.NotFoundException;
import com.rimmelasghar.boilerplate.springboot.model.Rental;
import com.rimmelasghar.boilerplate.springboot.model.Review;
import com.rimmelasghar.boilerplate.springboot.model.User;
import com.rimmelasghar.boilerplate.springboot.repository.RentalRepository;
import com.rimmelasghar.boilerplate.springboot.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ReviewMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Mapping(target = "user", expression = "java(getUserById(reviewDto.getUser_id()))")
    @Mapping(target = "rental", expression = "java(getRentalById(reviewDto.getRental_id()))")
    @Mapping(source = "created_at", target = "createdAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    public abstract Review toReview(ReviewDto reviewDto);

    @Mapping(source = "user.id", target = "user_id")
    @Mapping(source = "rental.id", target = "rental_id")
    @Mapping(source = "createdAt", target = "created_at")
    public abstract ReviewDto toReviewDto(Review review);

    public abstract void updateReviewFromDto(ReviewUpdateDto reviewUpdateDto, @MappingTarget Review review);

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
