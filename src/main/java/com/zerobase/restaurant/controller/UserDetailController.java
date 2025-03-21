package com.zerobase.restaurant.controller;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.dto.userDetail.SignInRequestDto;
import com.zerobase.restaurant.dto.userDetail.SignUpRequestDto;
import com.zerobase.restaurant.enums.CustomError;
import com.zerobase.restaurant.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserDetailController {
    private final UserDetailService userDetailService;

    @PostMapping("/signup")//새로운 회원을 생성
    public ResponseEntity<?> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        //body 값이 제대로 전달 되었는지 확인
        if(!signUpRequestDto.isValid()) throw new IllegalArgumentException(CustomError.BAD_REQUEST.name());
        ResponseDto<?> result = userDetailService.signUpUser(signUpRequestDto);//service에 dto 전달
        return ResponseEntity.ok(result);//결과 return
    }

    @PostMapping("/signin")//새로운 토큰을 발급(생성)
    public ResponseEntity<?> signin(@RequestBody SignInRequestDto requestDto) {
        if(!requestDto.isValid()) throw new IllegalArgumentException(CustomError.BAD_REQUEST.name());
        ResponseDto<?> result = userDetailService.signInUser(requestDto);
        return ResponseEntity.ok(result);
    }
}
