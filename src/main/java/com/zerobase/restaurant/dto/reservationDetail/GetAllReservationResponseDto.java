package com.zerobase.restaurant.dto.reservationDetail;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllReservationResponseDto {
    private UUID reservationId;
    private String restaurantName;
    private LocalDate date;
    private LocalTime time;
    private Integer tableNumber;
}
