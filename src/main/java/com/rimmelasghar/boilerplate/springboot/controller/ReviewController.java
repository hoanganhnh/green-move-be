package com.rimmelasghar.boilerplate.springboot.controller;

import com.rimmelasghar.boilerplate.springboot.dto.ReviewDto;
import com.rimmelasghar.boilerplate.springboot.dto.ReviewUpdateDto;
import com.rimmelasghar.boilerplate.springboot.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Tag(name = "Reviews", description = "API for managing reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Create a new review", description = "Creates a new review with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review created successfully",
            content = @Content(schema = @Schema(implementation = ReviewDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User or Rental not found")
    })
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto createdReview = reviewService.createReview(reviewDto);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @Operation(summary = "Get review by ID", description = "Returns review details for the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review found",
            content = @Content(schema = @Schema(implementation = ReviewDto.class))),
        @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable("review_id") Long reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(reviewDto);
    }

    @Operation(summary = "Get all reviews", description = "Returns a list of all reviews")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of reviews retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "Get reviews by user ID", description = "Returns a list of reviews for the specified user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of reviews retrieved successfully")
    })
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<ReviewDto>> getReviewsByUserId(@PathVariable("user_id") Long userId) {
        List<ReviewDto> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "Get reviews by rental ID", description = "Returns a list of reviews for the specified rental")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of reviews retrieved successfully")
    })
    @GetMapping("/rental/{rental_id}")
    public ResponseEntity<List<ReviewDto>> getReviewsByRentalId(@PathVariable("rental_id") Long rentalId) {
        List<ReviewDto> reviews = reviewService.getReviewsByRentalId(rentalId);
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "Update review", description = "Updates an existing review with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review updated successfully",
            content = @Content(schema = @Schema(implementation = ReviewDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @PutMapping("/{review_id}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable("review_id") Long reviewId,
            @Valid @RequestBody ReviewUpdateDto reviewUpdateDto) {
        ReviewDto updatedReview = reviewService.updateReview(reviewId, reviewUpdateDto);
        return ResponseEntity.ok(updatedReview);
    }

    @Operation(summary = "Delete review", description = "Deletes the review with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @DeleteMapping("/{review_id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("review_id") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
