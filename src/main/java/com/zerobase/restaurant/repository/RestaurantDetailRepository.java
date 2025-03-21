package com.zerobase.restaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.restaurant.dto.restaurantDetail.SaveRestaurantRequestDto;
import com.zerobase.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import static com.zerobase.restaurant.entity.QRestaurant.restaurant;


@Repository
@Slf4j
@RequiredArgsConstructor
public class RestaurantDetailRepository {
    private final RestaurantRepository restaurantRepository;
    private final JPAQueryFactory queryFactory;

    public void save(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public boolean alreadySaved(Restaurant inputData) {
        return queryFactory.selectFrom(restaurant)
                .where(restaurant.name.eq(inputData.getName()))
                .where(restaurant.lat.eq(inputData.getLat()))
                .where(restaurant.lon.eq(inputData.getLon()))
                .where(restaurant.partnerId.eq(inputData.getPartnerId()))
                .fetchOne() != null;
    }
}
