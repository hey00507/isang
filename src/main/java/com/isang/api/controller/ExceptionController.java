package com.isang.api.controller;


import com.isang.api.response.ErrorResponse;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody // rest통신시 @ResponseBody 어노테이션을 통해서 가능하다
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
//        MethodArgumentNotValidException
            ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다.");

            for(FieldError fieldError : e.getFieldErrors()){
                        response.addValidation(fieldError.getField(),fieldError.getDefaultMessage());
            }
           

        return  response;
    }
}
