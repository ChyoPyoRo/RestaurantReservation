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
    @Column(nullable = false, name = "reservation_id")
    private UUID reservationId;
    @Column(nullable=false)
    private Double score;
    @Column
    private String comment;

    public void update(Double score, String comment) {
        this.score = score;
        this.comment = comment;
    }
}
