package com.zerolab.bookecho.exception;

public class ReviewNotFound extends BookEchoException {

    private static final String MESSAGE = "존재하지 않는 리뷰입니다.";

    public ReviewNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
