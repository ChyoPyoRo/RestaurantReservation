package com.zerobase.restaurant.dto.userDetail;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter//값을 불러오기 위한 getter
@Setter//값을 저장하기 위한 setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)//CamelCase를 SnakeCase로 바꾸어 주는것
public class SignUpRequestDto {
    private String phoneNumber;
    private String password;
    private String roleType;

    public boolean isValid() {
        //hasText >> null & isEmpty 체크
        return StringUtils.hasText(phoneNumber)
                && StringUtils.hasText(password)
                && StringUtils.hasText(roleType);
    }
}
