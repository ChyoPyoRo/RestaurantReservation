package com.zerobase.restaurant.entity;

import com.zerobase.restaurant.dto.restaurantDetail.UpdateRestaurantRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String name;
    private UUID partnerId;//가게를 등록한 Partner 회원, Not FKey
    @Column(precision = 10, scale = 6)
    private BigDecimal lat;//y좌표
    @Column(precision = 10, scale = 6)
    private BigDecimal lon;//x좌표
    private String explanation;
    @Column(nullable = false, name = "table_count")
    private Integer tableCount;

    public void update(UpdateRestaurantRequestDto restaurant) {
        this.name = restaurant.getName();
        this.lat = restaurant.getLat();
        this.lon = restaurant.getLon();
        this.explanation = restaurant.getExplanation();
        this.tableCount = restaurant.getTableCount();
    }
}
