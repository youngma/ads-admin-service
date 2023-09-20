package com.ads.main.core.enums.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public enum AdCampaignException {

    AD_NOT_FOUND("등록된 광고를 찾을 수 없습니다."),
    AD_SMART_AD_VALID_ERROR("스마트 쇼핑 광고 요청이 잘못되었습니다."),
    AD_CAMPAIGN_STATUS_NOT_CHANGE("해당 상태로 변경할 수 없습니다."),

    ;

    private String message;

    AdCampaignException(String message) {
        this.message = message;
    }


    public UsernameNotFoundException throwErrors() {
        return new UsernameNotFoundException(this.message);
    }


}
