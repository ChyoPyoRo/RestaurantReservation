package com.zerobase.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomError {
    //전체 에러
    BAD_REQUEST("E0001", "잘못된 요청입니다."),
    UNEXPECTED_ERROR("E0002", "예상치 못한 에러입니다"),
    EMPTY_BODY("E0003", "Body가 잘못되었습니다"),
    //JWT 관련 에러
    HEADER_WITHOUT_TOKEN("E0101", "헤더에 토큰값이 존재하지 않습니다"),
    JWT_TOKEN_INVALID("E0102", "jwt토큰 형식이 잘못 되었습니다"),
    JWT_EXPIRED("E0103", "Token이 만료되었습니다"),
    //user관련 에러
    UNAUTHORIZED("E0201", "접근 권한이 없습니다."),
    WRONG_ROLE_TYPE("E0202", "잘못된 역할 값입니다."),
    ALREADY_SIGN_UP("E0203", "이미 회원 가입한 대상입니다."),
    WRONG_LOGIN_INFO("E0204", "로그인 정보가 잘못 되었습니다."),
    //restaurant 관련 에러
    ALREADY_SAVED_RESTAURANT("E0301", "이미 저장된 가게입니다")
    ;

    private final String errorCode;
    private final String errorMessage;
}
