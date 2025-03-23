package com.zerobase.restaurant.service;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.reviewDetail.PostReviewRequestDto;
import com.zerobase.restaurant.entity.Reservation;
import com.zerobase.restaurant.entity.Review;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.repository.ReviewDetailRepository;
import com.zerobase.restaurant.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReviewDetailService {
    private final ReviewDetailRepository reviewDetailRepository;

    public ResponseDto saveReview(String inputReservationId, PostReviewRequestDto requestDto) throws IllegalAccessException {
        UUID reservationId = UUID.fromString(inputReservationId);
        UUID loginUser = UserUtil.getUserIdWithUser();
        Reservation reservation = reviewDetailRepository.findReservationByReservationId(reservationId);
        if(reservation == null) throw new NoSuchElementException(CustomError.RESERVATION_NO_EXIST.name());
        if(!reservation.getUserId().equals(loginUser)) throw new IllegalAccessException(CustomError.UNAUTHORIZED.name());
        if(!reservation.isArrived()) throw new IllegalStateException(CustomError.RESERVATION_NO_SHOW.name());//입장하지 않은 예약은 리뷰 불가능
        //모든 조건이 완료되면 동일한 예약에 대해서 리뷰가 이미 존재하는지 확인
        if(reviewDetailRepository.checkAleardyReviewedReservation(reservation)) throw new IllegalStateException(CustomError.ALREADY_REVIEWED.name());
        //리뷰 생성
        Review review = Review.builder()
                .reservationId(reservationId)
                .score(requestDto.getScore())
                .comment(requestDto.getComment() != null? requestDto.getComment() : "")//사용자가 내용을 입력하지 않으면 빈 문자열로 진행
                .build();
        reviewDetailRepository.save(review);
        return ResponseDto.success();
    }
}
