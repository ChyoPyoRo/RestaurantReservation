package com.zerobase.restaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.restaurant.dto.reservationDetail.MakeReservationRequestDto;
import com.zerobase.restaurant.entity.Reservation;
import com.zerobase.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.zerobase.restaurant.entity.QReservation.reservation;

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
}
