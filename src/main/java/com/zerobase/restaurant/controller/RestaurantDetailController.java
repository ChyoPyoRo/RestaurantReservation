package com.zerobase.restaurant.controller;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.GetAllRestaurantResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.SaveRestaurantRequestDto;
import com.zerobase.restaurant.dto.restaurantDetail.UpdateRestaurantRequestDto;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.service.RestaurantDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/restaurants")
    public ResponseEntity<?> getAllRestaurant(@RequestParam(defaultValue = "1") Integer page) {//page값 전달 x 시 처음 페이지로 전달
        if(page < 1) page = 1;//1번째 페이지 이전의 값이 오면 초반 페이지로 고정
        ResponseDto<?> result = restaurantDetailService.getAllRestaurants(page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> getRestaurantById(@PathVariable String restaurantId) {
        if(restaurantId == null || restaurantId.isEmpty()) throw new IllegalArgumentException(CustomError.BAD_REQUEST.name());
        ResponseDto<?> result = restaurantDetailService.getDetailRestaurant(restaurantId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> updateRestaurntInfo(@PathVariable String restaurantId, @RequestBody UpdateRestaurantRequestDto requestDto) throws IllegalAccessException {
        if(!requestDto.isValid()) throw new IllegalArgumentException(CustomError.BAD_REQUEST.name());//pathVariable은 null값이 올 수 없음
        ResponseDto<?> result = restaurantDetailService.updateRestaurant(restaurantId, requestDto);
        return ResponseEntity.ok(result);
    }
}
