package com.zerobase.restaurant.dto.restaurantDetail;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class UpdateRestaurantRequestDto {
    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String explanation;

    public void setLat(BigDecimal lat) {
        this.lat = normalizeCoordinate(lat);
    }

    public void setLon(BigDecimal lon) {
        this.lon = normalizeCoordinate(lon);
    }
    //소숫점 자리를 Entity와 일치시키기 위한 메서드
    private BigDecimal normalizeCoordinate(BigDecimal value) {
        if (value == null) return null;
        return value.setScale(6, RoundingMode.HALF_UP); // 소수점 6자리, 반올림
    }

}
