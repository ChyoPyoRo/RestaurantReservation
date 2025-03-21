package com.zerobase.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    //사용자 권한
    ADMIN("ADMIN"),USER("USER"), PARTNER("PARTNER") ;
    private final String role;
    public static RoleType from(String role) {
        //전달된 문자열과 일치하는 RoleType 반환
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getRole().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(CustomError.WRONG_ROLE_TYPE.name()));//메세지 형태로 전달
    }
}
