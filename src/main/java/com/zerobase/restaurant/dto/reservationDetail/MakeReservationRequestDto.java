package com.zerobase.restaurant.dto.reservationDetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class MakeReservationRequestDto {
    //현재 예약 시스템은 좌석 랜덤 선택
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public boolean isValid() {
        return this.date != null && this.time != null;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(this.date, this.time);
    }

}
