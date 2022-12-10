package com.isang.common.exception.custom;

import com.isang.common.exception.IsangException;
import lombok.Getter;

/**
 * status -> 400
 */

@Getter
public class InvalidRequest extends IsangException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest(){super(MESSAGE);}

    public InvalidRequest(String fieldName, String message){
        super(MESSAGE);
        addValidation(fieldName,message);
    }
    @Override
    public String getStatusCode(){
        return "400";
    }
}
