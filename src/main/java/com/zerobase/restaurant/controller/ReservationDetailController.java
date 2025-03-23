package com.zerobase.restaurant.controller;

import com.zerobase.restaurant.dto.reservationDetail.MakeReservationRequestDto;
import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.service.ReservationDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReservationDetailController {
    private final ReservationDetailService reservationDetailService;

    @PostMapping("/reservation/{restaurantId}")
    public ResponseEntity<?> makeReservation(@PathVariable String restaurantId, @RequestBody MakeReservationRequestDto requestDto) throws IllegalAccessException {
        if(!requestDto.isValid()) throw new IllegalArgumentException(CustomError.BAD_REQUEST.name());
        ResponseDto<?> result = reservationDetailService.makeReservation(requestDto, restaurantId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/reservation/{reservationId}")//이미 생성된 reservation를 변경하는 것
    public ResponseEntity<?> updateReservationState(@PathVariable String reservationId) throws IllegalAccessException {
        ResponseDto<?> result = reservationDetailService.updateReservation(reservationId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reservation")
    public ResponseEntity<?> getAllReservationsOfUser() throws IllegalAccessException {
        ResponseDto<?> result = reservationDetailService.getAllReservationOfCurrentUser();
        return ResponseEntity.ok(result);
    }
}
