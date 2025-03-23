package com.zerobase.restaurant.service;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.GetAllRestaurantResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.GetRestaurantDetailResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.SaveRestaurantRequestDto;
import com.zerobase.restaurant.dto.restaurantDetail.UpdateRestaurantRequestDto;
import com.zerobase.restaurant.entity.Restaurant;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.repository.RestaurantDetailRepository;
import com.zerobase.restaurant.utils.UserUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
                .tableCount(requestDto.getTableCount())
                .build();
        //동일한 주인이 동일한 위치에 동일한 가게를 등록할 수 없도록 검증
        if(restaurantDetailRepository.alreadySaved(restaurant)) throw new EntityExistsException(CustomError.ALREADY_SAVED_RESTAURANT.name());
        restaurantDetailRepository.save(restaurant);
        //성공 response
        return ResponseDto.success();
    }

    public ResponseDto<?> getAllRestaurants(Integer page) {
        int pageSize = 10;//한 페이지에 10개 가게
        Pageable pageable = PageRequest.of(page-1, pageSize);//사용자에게 1쪽으로, 시스템에선 0부터 시작
        Page<GetAllRestaurantResponseDto> result = restaurantDetailRepository.getAllRestaurants(pageable);
        return ResponseDto.success(result);
    }

    public ResponseDto<?> getDetailRestaurant(String restaurantId) {
        GetRestaurantDetailResponseDto response = restaurantDetailRepository.getDetailRestaurant(UUID.fromString(restaurantId));
        if(response == null) throw new NoSuchElementException(CustomError.NO_SUCH_RESTAURANT.name());
        return ResponseDto.success(response);
    }

    public ResponseDto<?> updateRestaurant(String restaurantId, UpdateRestaurantRequestDto requestDto) throws IllegalAccessException {
        Restaurant restaurant = restaurantDetailRepository.getRestaurantEntity(UUID.fromString(restaurantId));
        if(restaurant == null) throw new NoSuchElementException(CustomError.NO_SUCH_RESTAURANT.name());
        if(!restaurant.getPartnerId().equals(UserUtil.getUserIdWithPartner()) ) throw new IllegalAccessException(CustomError.UNAUTHORIZED.name());//해당 가게의 등록자가 아님
        restaurant.update(requestDto);//영속성 컨텍스트에서 변화가 감지되서 update
        return ResponseDto.success();
    }

    public ResponseDto<?> deleteRestaurant(String restaurantId) throws IllegalAccessException {
        UUID userId = UserUtil.getUserIdWithPartner();
        long deletedRows =restaurantDetailRepository.deleteRestaurant(UUID.fromString(restaurantId), userId);//식당 uuid와 등록한 사람 uuid가 일치할 경우만 삭제
        if(deletedRows == 0) throw new NoSuchElementException(CustomError.DELETED_ERROR.name());//삭제된 식당이 없으면 에러 반환
        return ResponseDto.success();
    }
}
