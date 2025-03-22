package com.zerobase.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(name="user_id", nullable = false)
    private UUID userId;
    @Column(name="restaurant_id", nullable=false)
    private UUID restaurantId;//FKey 사용 안함
    @Column(nullable=false)
    private Double score;
    @Column
    private String comment;
}
