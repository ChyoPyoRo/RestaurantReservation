package com.zerobase.restaurant.dto.restaurantDetail;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRestaurantDetailResponseDto {
    private UUID restaurantId;
    private String name;
    private Double lat;
    private Double lon;
}
