package com.isang.common.exception.custom;

import com.isang.common.exception.IsangException;
import org.springframework.http.HttpStatus;

/**
 * status -> 404
 */
public class PostNotFound extends IsangException {

    private static final  String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
