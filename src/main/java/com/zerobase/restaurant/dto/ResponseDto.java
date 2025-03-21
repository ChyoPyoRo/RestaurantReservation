package com.zerobase.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zerobase.restaurant.enums.Description;
import com.zerobase.restaurant.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {//항상 일정한 response를 보내주기 위한 class
    private int code; //http status 값
    private Description description; //실제 성공 여부
    @JsonInclude(JsonInclude.Include.NON_NULL)//값이 없으면 보내지 않음
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;//커스텀 에러 코드
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;

    //Builder 패턴 사용하면 코드들이 더러워지므로 3개의 팩토리 메서드 생성

    //결과값 없는 성공 return
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(200, Description.SUCCESS, data, null, null);
    }

    public static ResponseDto<Void> success() {
        return new ResponseDto<>(200, Description.SUCCESS, null, null, null);
    }

    public static ResponseDto<Void> error(HttpStatus status, ErrorCode errorCode) {
        return new ResponseDto<>(status.value(), Description.FAIL, null, errorCode.getErrorCode(), errorCode.getErrorMessage());
    }
}
