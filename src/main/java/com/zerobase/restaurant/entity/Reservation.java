package com.zerobase.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(//동시성 관리 (RaceCondition 방지)
        name = "reservation",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"restaurant_id", "date", "time", "table_number"}
        )
)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(name="restaurant_id",nullable = false)
    private UUID restaurantId;
    @Column(name="user_id",nullable = false)
    private UUID userId;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime time;
    @Builder.Default
    private boolean isArrived=false;//처음 생성 시엔 무조건 false
    @Column(nullable = false, name ="table_number")
    private Integer tableNumber;//좌석 번호


    //예약 도착 확인 메서드
    public void updateArrivedState(){
        this.isArrived=true;
    }
}
