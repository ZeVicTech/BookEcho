package com.zerolab.bookecho.exception;

public class BookNotFound extends BookEchoException {

    private static final String MESSAGE = "존재하지 않는 책입니다.";

    public BookNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
