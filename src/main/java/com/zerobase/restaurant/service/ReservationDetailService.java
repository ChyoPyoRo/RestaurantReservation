package com.zerobase.restaurant.service;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.reservationDetail.MakeReservationRequestDto;
import com.zerobase.restaurant.entity.Reservation;
import com.zerobase.restaurant.entity.Restaurant;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.repository.ReservationDetailRepository;
import com.zerobase.restaurant.repository.RestaurantDetailRepository;
import com.zerobase.restaurant.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReservationDetailService {
    private final ReservationDetailRepository reservationDetailRepository;
    private final RestaurantDetailRepository restaurantDetailRepository;

    public ResponseDto<?> makeReservation(MakeReservationRequestDto requestDto, String inputRestaurantId) throws IllegalAccessException {
        UUID user = UserUtil.getUserIdWithUser();//user만 가능
        UUID restaurantId = UUID.fromString(inputRestaurantId);
        //해당 가게가 존재하는 가게인지 확인
        Restaurant restaurant = restaurantDetailRepository.getRestaurantEntity(restaurantId);
        if (restaurant == null) throw new IllegalAccessException(CustomError.NO_SUCH_RESTAURANT.name());
        //입력 받은 시간이 현재 시간 이전 인지 확인
        if(requestDto.getDateTime().isBefore(LocalDateTime.now())) throw new IllegalStateException(CustomError.RESERVATION_CLOSED.name());
        //해당 시간대에 예약 가능한지 확인
        List<Integer> reservatedTableNumber = reservationDetailRepository.getReservatedTableNumber(requestDto, restaurant);//이미 나간 테이블 조회
        List<Integer> allTables = IntStream.rangeClosed(1,restaurant.getTableCount()).boxed().collect(Collectors.toList());//전체테이블 리스트 생성
        allTables.removeAll(reservatedTableNumber);//예약된 테이블 리스트 삭제
        if(allTables.isEmpty()) throw new IllegalStateException(CustomError.RESERVATION_FULL.name());
        Reservation reservation = Reservation.builder()
                .restaurantId(restaurantId)
                .userId(user)
                .date(requestDto.getDate())
                .time(requestDto.getTime())
                .tableNumber(allTables.get(new Random().nextInt(allTables.size())))//가능한 좌석중에 랜덤 선택
                .build();
        reservationDetailRepository.saveReservation(reservation);
        return ResponseDto.success();
    }
}
