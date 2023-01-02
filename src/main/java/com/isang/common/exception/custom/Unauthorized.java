package com.isang.common.exception.custom;

import com.isang.common.exception.IsangException;

/**
 * status -> 401
 */
public class Unauthorized extends IsangException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public Unauthorized(){
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
       return 401;
    }
}
