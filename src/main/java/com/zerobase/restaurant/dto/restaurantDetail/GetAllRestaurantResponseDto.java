package com.zerobase.restaurant.dto.restaurantDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetAllRestaurantResponseDto {
    private String restaurantName;
    private UUID restaurantUuid;
    private Double lat;
    private Double lon;
    private Double score;//리뷰가 없으면 null로 저장
}
