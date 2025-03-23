package com.zerobase.restaurant.repository;

import com.querydsl.jpa.JPAExpressions;
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

    public Review getReviewById(UUID reviewId) {
        return queryFactory.selectFrom(review)
                .where(review.uuid.eq(reviewId))
                .fetchOne();
    }

    public boolean checkReviewOwner(UUID reviewId, UUID loginUser) {
        return queryFactory
                .selectFrom(review)
                .join(reservation).on(review.reservationId.eq(reservation.uuid))
                .where(
                        review.uuid.eq(reviewId),
                        reservation.userId.eq(loginUser)
                )
                .fetchFirst() != null;
    }

    public long deleteReview(UUID uuid, UUID loginUser) {
        return queryFactory.delete(review)
                .where(
                        review.uuid.eq(uuid),//REVIEW의 UUID가 동일한 것
                        review.reservationId.in(//예약한 사람과 REVIEW를 작성한 사람이 동일할 때
                                JPAExpressions
                                        .select(reservation.uuid)
                                        .from(reservation)
                                        .where(reservation.userId.eq(loginUser))
                        )
                )
                .execute();

    }

    public long deleteReviewByUser(UUID uuid) {
        return queryFactory.delete(review)
                .where(review.uuid.eq(uuid))
                .execute();
    }
}
