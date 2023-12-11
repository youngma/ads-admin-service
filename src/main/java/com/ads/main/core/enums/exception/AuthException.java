package com.ads.main.core.enums.exception;

import com.ads.main.core.config.exception.ServiceException;

public enum AuthException {

    PATH_NOT_FOUND("잘못된 접근 입니다."),
    ;

    private String message;

    AuthException(String message) {
        this.message = message;
    }


    public ServiceException throwErrors() {
        return new ServiceException(this.message);
    }

}
