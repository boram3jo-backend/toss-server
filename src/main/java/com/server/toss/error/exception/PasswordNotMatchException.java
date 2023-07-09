package com.server.toss.error.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String msg) {
        super(msg);
    }
}