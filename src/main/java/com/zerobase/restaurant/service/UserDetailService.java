package com.zerobase.restaurant.service;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.SignUpRequestDto;
import com.zerobase.restaurant.entity.UserData;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.enums.RoleType;
import com.zerobase.restaurant.repository.UserDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional//에러 발생 시 DB 원상복귀
@Service
@Slf4j
@RequiredArgsConstructor//DI 용
public class UserDetailService {
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder pwdEncoder;

    public ResponseDto<?> signUpUser(SignUpRequestDto requestDto){
        //해당 번호로 회원가입한 사람이 있는지 확인 후 에러 throw
        if(userDetailRepository.alreadyUsedPhoneNumber(requestDto.getPhoneNumber())) throw new IllegalArgumentException(CustomError.ALREADY_SIGN_UP.name());
        //객체 생성 후 저장
        UserData user = UserData.builder()
                .role(RoleType.from(requestDto.getRoleType()))
                .password(pwdEncoder.encode(requestDto.getPassword()))
                .phoneNumber(requestDto.getPhoneNumber())
                .build();
        userDetailRepository.save(user);
        return ResponseDto.success();
    }

}
