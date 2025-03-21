package com.zerobase.restaurant.utils;

import com.zerobase.restaurant.dto.UserSessionDto;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.enums.RoleType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

@Component
public class UserUtil {
    public static UserSessionDto getUserSessionDto() {
        //Spring Security와 jwt를 통해 저장된 User정보를 가져와서 Dto에 매칭
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userUuid = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return UserSessionDto.builder()
                .userUuid(userUuid)
                .roleType(authorities.iterator().next().toString())
                .build();
    }

    public static UUID getUserIdWithPartnerOrAdmin() throws IllegalAccessException {
        UserSessionDto userSessionDto = getUserSessionDto();
        String auth = userSessionDto.getRoleType();

        if(RoleType.ADMIN.getRole().equals(auth) || RoleType.PARTNER.getRole().equals(auth)) {
            return UUID.fromString(userSessionDto.getUserUuid());
        }
        else
            throw new IllegalAccessException(CustomError.UNAUTHORIZED.name());
    }
    public static UUID getUserIdWithPartner() throws IllegalAccessException {
        UserSessionDto userSessionDto = getUserSessionDto();
        String auth = userSessionDto.getRoleType();
        if(RoleType.PARTNER.getRole().equals(auth)){
            return UUID.fromString(userSessionDto.getUserUuid());
        }
        else throw new IllegalAccessException(CustomError.UNAUTHORIZED.name());
    }
    public static UUID getUserIdWithUser() throws IllegalAccessException {
        UserSessionDto userSessionDto = getUserSessionDto();
        String auth = userSessionDto.getRoleType();
        if(RoleType.USER.getRole().equals(auth)){
            return UUID.fromString(userSessionDto.getUserUuid());
        }
        else throw new IllegalAccessException(CustomError.UNAUTHORIZED.name());
    }
}
