package com.zerobase.restaurant.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignInRequestDto {
    private String phoneNumber;
    private String password;

    public boolean isValid(){
        return StringUtils.hasText(phoneNumber) && StringUtils.hasText(password);
    }
}
