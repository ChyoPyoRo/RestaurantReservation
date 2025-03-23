package com.zerobase.restaurant.service;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.reservationDetail.GetAllReservationResponseDto;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        if (restaurant == null) throw new IllegalStateException(CustomError.NO_SUCH_RESTAURANT.name());
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

    public ResponseDto<?> updateReservation(String reservationId) throws IllegalAccessException {
        UUID loginUser = UserUtil.getUserIdWithUser();
        Reservation reservation = reservationDetailRepository.getReservation(UUID.fromString(reservationId));
        if(reservation == null) throw new IllegalStateException(CustomError.RESERVATION_NO_EXIST.name());//해당 예약이 존재하지 않음
        if(!reservation.getUserId().equals(loginUser) ) throw new IllegalAccessException(CustomError.UNAUTHORIZED.name());//해당 예약을 편집할 권한이 없어
        //이미 처리된 예약인지 확인
        if(reservation.isArrived()) throw new IllegalStateException(CustomError.RESERVATION_ALREADY_USED.name());
        //날짜 검증
        LocalDate today = LocalDate.now();//현재 날짜
        if(reservation.getDate().isBefore(today)) throw new IllegalStateException(CustomError.RESERVATION_NOT_OPEN.name());//입장 가능 날짜 아님
        //시간 검증
        LocalTime nowTime = LocalTime.now();//현재 시간
        LocalTime reservedTime = reservation.getTime();//예약 시간
        LocalTime windowStart = reservedTime.minusMinutes(10);
        LocalTime windowEnd = reservedTime.plusMinutes(10);
        if(nowTime.isBefore(windowStart)) throw new IllegalStateException(CustomError.RESERVATION_NOT_OPEN.name());//10분전에 입장 불가능
        if(nowTime.isAfter(windowEnd)) throw new IllegalStateException(CustomError.RESERVATION_CLOSED.name());//10분 이후에 불가능
        reservation.updateArrivedState();//영속성 컨텍스트에서 상태변화 감지해서 변경
        return ResponseDto.success();
    }

    public ResponseDto<?> getAllReservationOfCurrentUser() throws IllegalAccessException {
        UUID loginUser = UserUtil.getUserIdWithUser();
        List<GetAllReservationResponseDto> result = reservationDetailRepository.getAllReservationByUserId(loginUser);
        return ResponseDto.success(result);
    }
}
