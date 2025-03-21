package com.zerobase.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    //사용자 권한
    ADMIN("ADMIN"),USER("USER"), PARTNER("PARTNER") ;
    private final String role;
}
