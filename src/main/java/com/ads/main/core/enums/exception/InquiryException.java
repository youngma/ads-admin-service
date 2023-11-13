package com.ads.main.core.enums.exception;

public enum InquiryException {

    INQUIRY_NOT_FOUND("등록된 문의사항이 없습니다."),

;

    private String message;

    InquiryException(String message) {
        this.message = message;
    }


    public RuntimeException throwErrors() {
        return new RuntimeException(this.message);
    }



}
