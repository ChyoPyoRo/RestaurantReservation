package com.zerobase.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomError {
    //전체 에러
    BAD_REQUEST("E0001", "잘못된 요청입니다."),
    UNEXPECTED_ERROR("E0002", "예상치 못한 에러입니다"),
    EMPTY_BODY("E0003", "Request형식이 잘못되었거나 비었습니다"),
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
    ALREADY_SAVED_RESTAURANT("E0301", "이미 저장된 가게입니다"),
    NO_SUCH_RESTAURANT("E0302", "해당 ID 값을 가지는 식당이 없습니다"),
    DELETED_ERROR("E0303", "해당 식당이 존재하지 않거나 권한이 없습니다."),
    //reservation 관련 에러
    RESERVATION_NOT_OPEN("E0401","아직 입장 가능 시간이 아닙니다."),
    RESERVATION_EXPIRED("E0402","이미 만료된 예약입니다."),
    RESERVATION_FULL("E0403", "해당 시간에 예약 가능한 테이블이 없습니다."),
    RESERVATION_CLOSED("E0404", "현재 예약할 수 없는 예약입니다.")
    ;

    private final String errorCode;
    private final String errorMessage;
}
