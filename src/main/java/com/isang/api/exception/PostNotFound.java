package com.isang.api.exception;

/**
 * status -> 400
 */
public class PostNotFound extends IsangException{

    private static final  String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "404";
    }
}
