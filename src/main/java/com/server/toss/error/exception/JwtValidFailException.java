package com.server.toss.error.exception;

public class JwtValidFailException extends RuntimeException {
    public JwtValidFailException(String msg) {
        super(msg);
    }
}