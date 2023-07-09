package com.server.toss.error;

import com.server.toss.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
    /**
     * 패키지 명을 제외한 클래스 이름을 반환한다.
     *
     * @param e 에러
     * @return
     */
    private static String getSimpleName(Exception e) {
        return e.getClass().getSimpleName();
    }

    @ResponseStatus(HttpStatus.CONFLICT) // 반환할 상태코드 설정한다.
    @ExceptionHandler(DuplicateEmailException.class) // 처리할 에러를 설정한다.
    public ErrorMsg handleDuplicateIdException(DuplicateEmailException e) {
        // Exception 객체의 현지화 메시지와 클래스 이름을 반환한다.
        return new ErrorMsg(e.getLocalizedMessage(), getSimpleName(e));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 반환할 상태코드 설정한다.
    @ExceptionHandler(RequestNullDataException.class) // 처리할 에러를 설정한다.
    public ErrorMsg handleRequestNullDataException(RequestNullDataException e) {
        // Exception 객체의 현지화 메시지와 클래스 이름을 반환한다.
        return new ErrorMsg(e.getLocalizedMessage(), getSimpleName(e));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND) // 반환할 상태코드 설정한다.
    @ExceptionHandler(UserNotFoundException.class) // 처리할 에러를 설정한다.
    public ErrorMsg handleUserNotFoundException(UserNotFoundException e) {
        // Exception 객체의 현지화 메시지와 클래스 이름을 반환한다.
        return new ErrorMsg(e.getLocalizedMessage(), getSimpleName(e));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 반환할 상태코드 설정한다.
    @ExceptionHandler(PasswordNotMatchException.class) // 처리할 에러를 설정한다.
    public ErrorMsg handlePasswordNotMatchException(PasswordNotMatchException e) {
        // Exception 객체의 현지화 메시지와 클래스 이름을 반환한다.
        return new ErrorMsg(e.getLocalizedMessage(), getSimpleName(e));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 반환할 상태코드 설정한다.
    @ExceptionHandler(JwtExpireException.class) // 처리할 에러를 설정한다.
    public ErrorMsg handleExpireException(JwtExpireException e) {
        // Exception 객체의 현지화 메시지와 클래스 이름을 반환한다.
        return new ErrorMsg(e.getLocalizedMessage(), getSimpleName(e));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 반환할 상태코드 설정한다.
    @ExceptionHandler(JwtValidFailException.class) // 처리할 에러를 설정한다.
    public ErrorMsg handleJwtValidFailException(JwtValidFailException e) {
        // Exception 객체의 현지화 메시지와 클래스 이름을 반환한다.
        return new ErrorMsg(e.getLocalizedMessage(), getSimpleName(e));
    }
}
