package com.ads.main.core.enums.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public enum PartnerException {

    PARTNER_NOT_FOUND("등록돤 매체사를 찾을 수 없습니다."),
    PARTNER_ALREADY_EXISTS("이미 등록된 사업자 번호 입니다."),
    PARTNER_ACCOUNT_NOT_FOUND("등록된 계좌 번호를 찾을 수 없습니다"),
    PARTNER_ACCOUNT_ALREADY_EXISTS("이미 등록된 계좌 번호 입니다"),
    PARTNER_AD_GROUP_NOT_FOUND("등록된 광고 그룹이 없습니다."),
    PARTNER_AD_GROUP_STATUS_NOT_CHANGE("해당 상태로 변경할 수 없습니다."),
    ;
            ;


    private final String message;

    PartnerException(String message) {
        this.message = message;
    }


    public UsernameNotFoundException throwErrors() {
        return new UsernameNotFoundException(this.message);
    }


}
