package com.isang.api.entity.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 * "code" : "400"
 * "message" : "잘못된 요청입니다."
 * "valodation" : {
 * "title" : "값을 입력해주세요."
 * }
 * }
 */


//@JsonInclude(value = JsonInclude.Include.NON_EMPTY) // 비어있지 않은 데이터만 리턴하겠다.
public record ErrorResponse(Integer code, String message, Map<String, String> validation) {

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

    @Builder
    public ErrorResponse(Integer code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }
}

