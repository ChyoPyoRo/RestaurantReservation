package com.zerobase.restaurant.controller;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.reviewDetail.UpdateReveiwRequestDto;
import com.zerobase.restaurant.dto.reviewDetail.PostReviewRequestDto;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.service.ReviewDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewDetailController {
    private final ReviewDetailService reviewDetailService;
    @PostMapping("/review/{reservationId}")
    public ResponseEntity<?> postReview(@PathVariable String reservationId, @RequestBody PostReviewRequestDto requestDto) throws IllegalAccessException {
        if(requestDto.isValid()) throw new IllegalAccessException(CustomError.BAD_REQUEST.name());
        ResponseDto responseDto = reviewDetailService.saveReview(reservationId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getAllReviews() throws IllegalAccessException {

        ResponseDto<?> responseDto = reviewDetailService.getAllReviewUserWrite();
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable String reviewId, @RequestBody UpdateReveiwRequestDto requestDto ) throws IllegalAccessException{
        if(!requestDto.isValid()) throw new IllegalAccessException(CustomError.BAD_REQUEST.name());
        ResponseDto<?> responseDto = reviewDetailService.updateReview(reviewId, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
