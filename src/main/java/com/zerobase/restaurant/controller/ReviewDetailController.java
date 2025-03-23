package com.zerobase.restaurant.controller;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.reviewDetail.PostReviewRequestDto;
import com.zerobase.restaurant.service.ReviewDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewDetailController {
    private final ReviewDetailService reviewDetailService;
    @PostMapping("/review/{reservationId}")
    public ResponseEntity<?> postReview(@PathVariable String reservationId, @RequestBody PostReviewRequestDto requestDto) throws IllegalAccessException {
        ResponseDto responseDto = reviewDetailService.saveReview(reservationId, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
