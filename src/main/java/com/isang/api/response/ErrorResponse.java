package com.isang.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code" : "400"
 *     "message" : "잘못된 요청입니다."
 *     "valodation" : {
 *         "title" : "값을 입력해주세요."
 *     }
 * }
 */


@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY) // 비어있지 않은 데이터만 리턴하겠다.
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String,String> validation;
    public void addValidation(String fieldName,String errorMessage){
        this.validation.put(fieldName,errorMessage);
    }

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }
}
