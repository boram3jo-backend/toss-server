package com.server.toss.error.exception;

public class RequestNullDataException extends RuntimeException {
    public RequestNullDataException(String msg) {
        super(msg);
    }
}