package com.zerobase.restaurant.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.restaurant.dto.reservationDetail.GetAllReservationResponseDto;
import com.zerobase.restaurant.dto.reservationDetail.MakeReservationRequestDto;
import com.zerobase.restaurant.entity.Reservation;
import com.zerobase.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.zerobase.restaurant.entity.QReservation.reservation;
import static com.zerobase.restaurant.entity.QRestaurant.restaurant;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ReservationDetailRepository {
    private final ReservationRepository reservationRepository;
    private final JPAQueryFactory queryFactory;

    public List<Integer> getReservatedTableNumber(MakeReservationRequestDto requestDto, Restaurant inputRestaurant) {
        return queryFactory
                .select(reservation.tableNumber)
                .from(reservation)
                .where(
                        reservation.restaurantId.eq(inputRestaurant.getUuid()),
                        reservation.date.eq(requestDto.getDate()),
                        reservation.time.eq(requestDto.getTime())
                )
                .fetch();
    }

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public Reservation getReservation(UUID reservationId) {
        return queryFactory.selectFrom(reservation)
                .where(reservation.uuid.eq(reservationId))
                .fetchOne();
    }

    public List<GetAllReservationResponseDto> getAllReservationByUserId(UUID loginUser) {
        return queryFactory.select(Projections.constructor(GetAllReservationResponseDto.class,
                        reservation.uuid.as("reservationId"),
                        restaurant.name.as("restaurantName"),
                        reservation.date,
                        reservation.time,
                        reservation.tableNumber
                        ))
                .from(reservation)
                .leftJoin(restaurant).on(restaurant.uuid.eq(reservation.restaurantId))
                .where(reservation.userId.eq(loginUser))
                .fetch();
    }
}
