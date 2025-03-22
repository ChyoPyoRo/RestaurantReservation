package com.zerobase.restaurant.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.GetAllRestaurantResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.GetRestaurantDetailResponseDto;
import com.zerobase.restaurant.dto.restaurantDetail.SaveRestaurantRequestDto;
import com.zerobase.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.zerobase.restaurant.entity.QRestaurant.restaurant;
import static com.zerobase.restaurant.entity.QReview.review;
import static com.zerobase.restaurant.entity.QReservation.reservation;


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

    public Page<GetAllRestaurantResponseDto> getAllRestaurants(Pageable pageable) {
        List<GetAllRestaurantResponseDto> content = queryFactory
                .select(Projections.constructor(GetAllRestaurantResponseDto.class,
                        restaurant.name,
                        restaurant.uuid,
                        restaurant.lat.doubleValue(),
                        restaurant.lon.doubleValue(),
                        review.score.avg()//review에 해당되는 row들 평균 가져오기
                        ))
                .from(restaurant)
                .leftJoin(reservation).on(restaurant.uuid.eq(reservation.reservationId))//Reservation 중에 restaurantId를 가지고 있는 row들
                .leftJoin(review).on(reservation.uuid.eq(review.reservationId))//Review중에 Reservation을 가지고 있는 row들
                .groupBy(restaurant.uuid, restaurant.name, restaurant.lat, restaurant.lon)
                .offset((pageable.getOffset()))
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.select(restaurant.count()).from(restaurant).fetchOne();//전체 가게 목록
        return new PageImpl<>(content, pageable, total);
    }


    public GetRestaurantDetailResponseDto getDetailRestaurant(UUID restaurantId) {
        return queryFactory.select(Projections.constructor(GetRestaurantDetailResponseDto.class,
                        restaurant.uuid.as("restaurantId"),
                        restaurant.name,
                        restaurant.lat.doubleValue(),//double타입으로 변환
                        restaurant.lon.doubleValue(),
                        restaurant.table_count
                        ))
                .from(restaurant)
                .where(restaurant.uuid.eq(restaurantId))
                .fetchOne();
    }

    public Restaurant getRestaurantEntity(UUID restaurantId) {
        return queryFactory.selectFrom(restaurant)
                .where(restaurant.uuid.eq(restaurantId))
                .fetchOne();
    }

    public long deleteRestaurant(UUID uuid, UUID userId) {
        return queryFactory.delete(restaurant)
                .where(restaurant.uuid.eq(uuid))//식당 uuid 값 일치
                .where(restaurant.partnerId.eq(userId))//partnerid일치
                .execute();//실행하고 삭제 수량 return
    }
}
