package com.isang.common.exception;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 최상위 Exception 을 통한 Exception Handling
 */

@Getter
public abstract class IsangException extends RuntimeException {

    private final Map<String,String> validation = new HashMap<>();
    public IsangException(String message) {
        super(message);
    }

    public IsangException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getStatusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName,message);
    };
}
