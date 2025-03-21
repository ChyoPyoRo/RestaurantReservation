package com.zerobase.restaurant.controller;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.SaveRestaurantRequestDto;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.service.RestaurantDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RestaurantDetailController {
    private final RestaurantDetailService restaurantDetailService;

    @PostMapping("/restaurant")
    public ResponseEntity<?> saveNewRestaurant(@RequestBody SaveRestaurantRequestDto requestDto) throws IllegalAccessException {
        if(!requestDto.isValid()) throw new IllegalArgumentException(CustomError.BAD_REQUEST.name());//null 확인
        ResponseDto<?> result = restaurantDetailService.saveNewRestaurant(requestDto);//식당 저장
        return ResponseEntity.ok(result);
    }
}
