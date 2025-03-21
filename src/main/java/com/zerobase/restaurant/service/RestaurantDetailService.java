package com.zerobase.restaurant.service;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.SaveRestaurantRequestDto;
import com.zerobase.restaurant.entity.Restaurant;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.repository.RestaurantDetailRepository;
import com.zerobase.restaurant.utils.UserUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RestaurantDetailService {
    private final RestaurantDetailRepository restaurantDetailRepository;

    public ResponseDto<?> saveNewRestaurant(SaveRestaurantRequestDto requestDto) throws IllegalAccessException {
        //Partner 회원인지 확인, 해당 메서드에서 권한 확인 완료
        UUID uuid = UserUtil.getUserIdWithPartner();
        //맞으면 가게 저장
        Restaurant restaurant = Restaurant.builder()
                .partnerId(uuid)
                .name(requestDto.getName())
                .explanation(requestDto.getExplanation())
                .lat(requestDto.getLat())
                .lon(requestDto.getLon())
                .build();
        //동일한 주인이 동일한 위치에 동일한 가게를 등록할 수 없도록 검증
        if(restaurantDetailRepository.alreadySaved(restaurant)) throw new EntityExistsException(CustomError.ALREADY_SAVED_RESTAURANT.name());
        restaurantDetailRepository.save(restaurant);
        //성공 response
        return ResponseDto.success();
    }

}
