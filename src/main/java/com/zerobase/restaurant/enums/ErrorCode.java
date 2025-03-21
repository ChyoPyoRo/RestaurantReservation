package com.zerobase.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    WRONG_TOKEN("E0101", "토큰 값이 전달되지 않았습니다."),
    UNAUTHORIZED("E0102", "접근 권한이 없습니다."),
    JWT_TOKEN_INVALID("E0103", "jwt토큰 형식이 잘못 되었습니다")
    ;

    private final String errorCode;
    private final String errorMessage;
}
