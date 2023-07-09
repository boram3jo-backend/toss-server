package com.server.toss.error.exception;

public class JwtExpireException extends RuntimeException {
    public JwtExpireException(String msg) {
        super(msg);
    }
}