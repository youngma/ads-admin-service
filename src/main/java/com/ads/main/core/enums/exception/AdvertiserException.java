package com.ads.main.core.enums.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public enum AdvertiserException {

    ADVERTISER_NOT_FOUND("등록돤 광고주를 찾을 수 없습니다."),
    ADVERTISER_ALREADY_EXISTS("이미 등록된 사업자 번호 입니다."),
    ADVERTISER_ACCOUNT_NOT_FOUND("등록된 계좌 번호를 찾을 수 없습니다"),
    ADVERTISER_ACCOUNT_ALREADY_EXISTS("이미 등록된 계좌 번호 입니다"),
    ;

    private String message;

    AdvertiserException(String message) {
        this.message = message;
    }


    public RuntimeException throwErrors() {
        return new RuntimeException(this.message);
    }



}
