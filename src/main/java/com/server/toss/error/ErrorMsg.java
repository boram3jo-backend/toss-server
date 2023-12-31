package com.server.toss.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorMsg {

    private String msg;
    private String errorCode;

    /**
     * 생성자
     *
     * @param msg 메시지
     * @param errorCode 에러코드
     */
    public ErrorMsg(String msg, String errorCode) {
        super();
        this.msg = msg;
        this.errorCode = errorCode;
    }
}
