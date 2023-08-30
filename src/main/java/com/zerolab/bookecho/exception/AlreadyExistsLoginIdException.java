package com.zerolab.bookecho.exception;

public class AlreadyExistsLoginIdException extends BookEchoException{

    private static final String MESSAGE = "중복된 로그인 아이디가 존재합니다.";

    public AlreadyExistsLoginIdException() {
        super(MESSAGE);
    }

    public AlreadyExistsLoginIdException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
