package com.rimmelasghar.boilerplate.springboot.service.Impl;

import com.rimmelasghar.boilerplate.springboot.dto.ReviewDto;
import com.rimmelasghar.boilerplate.springboot.dto.ReviewUpdateDto;
import com.rimmelasghar.boilerplate.springboot.exceptions.NotFoundException;
import com.rimmelasghar.boilerplate.springboot.mapper.ReviewMapper;
import com.rimmelasghar.boilerplate.springboot.model.Review;
import com.rimmelasghar.boilerplate.springboot.repository.ReviewRepository;
import com.rimmelasghar.boilerplate.springboot.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        // Set created_at if not provided
        if (reviewDto.getCreated_at() == null) {
            reviewDto.setCreated_at(LocalDateTime.now());
        }
        
        // Convert DTO to entity
        Review review = reviewMapper.toReview(reviewDto);
        
        // Save review
        Review savedReview = reviewRepository.save(review);
        
        // Return saved review as DTO
        return reviewMapper.toReviewDto(savedReview);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review not found with id: " + id));
        
        return reviewMapper.toReviewDto(review);
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toReviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(reviewMapper::toReviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getReviewsByRentalId(Long rentalId) {
        return reviewRepository.findByRentalId(rentalId).stream()
                .map(reviewMapper::toReviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewUpdateDto reviewUpdateDto) {
        // Find review by id
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review not found with id: " + id));
        
        // Update review properties
        reviewMapper.updateReviewFromDto(reviewUpdateDto, review);
        
        // Save updated review
        Review updatedReview = reviewRepository.save(review);
        
        // Return updated review as DTO
        return reviewMapper.toReviewDto(updatedReview);
    }

    @Override
    public void deleteReview(Long id) {
        // Check if review exists
        if (!reviewRepository.existsById(id)) {
            throw new NotFoundException("Review not found with id: " + id);
        }
        
        // Delete review
        reviewRepository.deleteById(id);
    }
    
    @Override
    public List<ReviewDto> getReviewsWithFilters(Long userId, Long rentalId, Integer minRating, Integer maxRating,
                                               LocalDateTime createdAtFrom, LocalDateTime createdAtTo) {
        Specification<Review> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Filter by user_id if provided
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }
            
            // Filter by rental_id if provided
            if (rentalId != null) {
                predicates.add(criteriaBuilder.equal(root.get("rental").get("id"), rentalId));
            }
            
            // Filter by minimum rating if provided
            if (minRating != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating));
            }
            
            // Filter by maximum rating if provided
            if (maxRating != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating));
            }
            
            // Filter by created_at date range if provided
            if (createdAtFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAtFrom));
            }
            
            if (createdAtTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAtTo));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        
        return reviewRepository.findAll(specification).stream()
                .map(reviewMapper::toReviewDto)
                .collect(Collectors.toList());
    }
}
