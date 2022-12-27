package com.isang.api.controller;


import com.isang.common.exception.IsangException;
import com.isang.api.entity.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
            ErrorResponse response = ErrorResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("잘못된 요청입니다.")
                    .build();

            for(FieldError fieldError : e.getFieldErrors()){
                        response.addValidation(fieldError.getField(),fieldError.getDefaultMessage());
            }

        return  response;
    }

    @ResponseBody
    @ExceptionHandler(IsangException.class)
    public ResponseEntity<ErrorResponse> IsangException(IsangException e){

        Integer statusCode = e.getStatusCode();
        
        
        ErrorResponse body = ErrorResponse.builder()
                .code(statusCode)
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();


        //응답 Json Validation 필드에 title : 제목에 '바보'를 포함할 수 없습니다.

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return  response;
    }
}
