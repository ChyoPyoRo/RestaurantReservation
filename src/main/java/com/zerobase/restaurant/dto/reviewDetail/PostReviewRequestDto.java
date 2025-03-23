package com.zerobase.restaurant.dto.reviewDetail;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PostReviewRequestDto {
    private Double score;
    private String comment;

    public boolean isValid(){
        return score!= null;//comment는 없을 수도 있음
    }
}
