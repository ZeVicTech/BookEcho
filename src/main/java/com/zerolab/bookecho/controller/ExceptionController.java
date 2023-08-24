package com.zerolab.bookecho.controller;

import com.zerolab.bookecho.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class) //인자가 유효하지 않을 때 발생하는 에러 클래스
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){

        ErrorResponse response = new ErrorResponse("400","잘못된 요청입니다.");

        for (FieldError fieldError : e.getFieldErrors()){
            response.addValidatoin(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return response;
    }
}

