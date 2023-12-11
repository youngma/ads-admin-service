package com.ads.main.core.enums.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public enum UserException {

    USER_NOT_FOUND("로그인 계정정보를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS("이미 등록된 사용자입니다."),
    PASSWORD_MISS_MATCH("이전 비밀번호가 틀립니다."),
    ;

    private String message;

    UserException(String message) {
        this.message = message;
    }


    public RuntimeException throwErrors() {
        return new RuntimeException(this.message);
    }

}
