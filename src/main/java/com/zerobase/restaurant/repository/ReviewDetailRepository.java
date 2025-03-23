package com.zerobase.restaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.restaurant.entity.Reservation;
import com.zerobase.restaurant.entity.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.zerobase.restaurant.entity.QReservation.reservation;
import static com.zerobase.restaurant.entity.QReview.review;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ReviewDetailRepository {
    private final ReviewRepository reviewRepository;
    private final JPAQueryFactory queryFactory;

    public Reservation findReservationByReservationId(UUID reservationId) {
        return queryFactory.selectFrom(reservation)
                .where(reservation.uuid.eq(reservationId))
                .fetchOne();
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public boolean checkAleardyReviewedReservation(Reservation reservation) {
        return queryFactory.selectFrom(review)
                .where(review.reservationId.eq(reservation.getUuid()))
                .fetchOne() != null;
    }

    public List<Review> getAllReviewByUserId(UUID loginUser) {
        return queryFactory.selectFrom(review)
                .leftJoin(reservation).on(review.reservationId.eq(reservation.uuid))
                .where(reservation.userId.eq(loginUser))
                .fetch();
    }
}
