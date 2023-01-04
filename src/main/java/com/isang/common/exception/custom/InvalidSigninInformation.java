package com.isang.common.exception.custom;

import com.isang.common.exception.IsangException;

public class InvalidSigninInformation extends IsangException {
    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public InvalidSigninInformation() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode(){
        return 400;
    }

}
