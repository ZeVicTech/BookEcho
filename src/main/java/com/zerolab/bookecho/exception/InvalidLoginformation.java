package com.zerolab.bookecho.exception;

public class InvalidLoginformation extends BookEchoException {

    private static final String MESSAGE = "아이디 혹은 비밀번호가 올바르지 않습니다.";

    public InvalidLoginformation(){
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
