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
}
