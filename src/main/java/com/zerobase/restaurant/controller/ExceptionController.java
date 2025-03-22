package com.zerobase.restaurant.controller;

import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.enums.CustomError;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseDto> handleNoSuchElementException(NoSuchElementException ex) {
        CustomError customError = CustomError.valueOf(ex.getMessage());//커스텀 객체로 생성
        log.error(customError.getErrorCode());//어떤 에러인지 확인
        log.error("발생 위치 : {}:{}",ex.getStackTrace()[0].getFileName(), ex.getStackTrace()[0].getLineNumber());//어디서 발생했는지 확인
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.CONFLICT, customError), HttpStatus.CONFLICT);//에러 response
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        CustomError customError = CustomError.valueOf(ex.getMessage());//커스텀 객체로 생성
        log.error(customError.getErrorCode());//어떤 에러인지 확인
        log.error("발생 위치 : {}:{}",ex.getStackTrace()[0].getFileName(), ex.getStackTrace()[0].getLineNumber());//어디서 발생했는지 확인
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.BAD_REQUEST, customError), HttpStatus.BAD_REQUEST);//에러 response
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(HttpMessageNotReadableException ex) {
        CustomError customError = CustomError.EMPTY_BODY;//커스텀 객체로 생성
        log.error(customError.getErrorCode());//어떤 에러인지 확인
        log.error("발생 위치 : {}:{}",ex.getStackTrace()[0].getFileName(), ex.getStackTrace()[0].getLineNumber());//어디서 발생했는지 확인
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.BAD_REQUEST, customError), HttpStatus.BAD_REQUEST);//에러 response
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalAccessException ex) {
        CustomError customError = CustomError.valueOf(ex.getMessage());
        log.error(customError.getErrorCode());//어떤 에러인지 확인
        log.error("발생 위치 : {}:{}",ex.getStackTrace()[0].getFileName(), ex.getStackTrace()[0].getLineNumber());//어디서 발생했는지 확인
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.UNAUTHORIZED, customError), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ResponseDto> handleEntityExistsException(EntityExistsException ex) {
        CustomError customError = CustomError.valueOf(ex.getMessage());//커스텀 객체로 생성
        log.error(customError.getErrorCode());//어떤 에러인지 확인
        log.error("발생 위치 : {}:{}",ex.getStackTrace()[0].getFileName(), ex.getStackTrace()[0].getLineNumber());//어디서 발생했는지 확인
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.CONFLICT, customError), HttpStatus.CONFLICT);//에러 response
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleException(Exception ex) {
        log.error("Detail : {}",ex);//개발용
        return new ResponseEntity<>(ResponseDto.error(HttpStatus.INTERNAL_SERVER_ERROR, CustomError.UNEXPECTED_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
