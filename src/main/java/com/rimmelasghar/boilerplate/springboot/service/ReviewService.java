package com.rimmelasghar.boilerplate.springboot.service;

import com.rimmelasghar.boilerplate.springboot.dto.ReviewDto;
import com.rimmelasghar.boilerplate.springboot.dto.ReviewUpdateDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getAllReviews();
    List<ReviewDto> getReviewsByUserId(Long userId);
    List<ReviewDto> getReviewsByRentalId(Long rentalId);
    ReviewDto updateReview(Long id, ReviewUpdateDto reviewUpdateDto);
    void deleteReview(Long id);
}
