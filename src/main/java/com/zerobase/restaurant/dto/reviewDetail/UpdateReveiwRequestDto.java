package com.zerobase.restaurant.dto.reviewDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReveiwRequestDto {
    private Double score;
    private String comment;

    public boolean isValid(){
        return score!= null;//comment는 없을 수도 있음
    }
}
