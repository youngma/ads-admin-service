package com.ads.main.core.enums.inquiry;

import lombok.Getter;

import java.util.Arrays;


@Getter
public enum InquiryType {

    Service("SERVICE", "퀴즈 광고 서비스", 2),
    Quiz("QUIZ", "퀴즈 광고 캠페인", 1),

    ;

    private final String code;
    private final String name;
    private final int order;

    InquiryType(String code, String name, int order) {
        this.code = code;
        this.name = name;
        this.order= order;
    }


    public static InquiryType of(final String code) {
        return Arrays.stream(InquiryType.values())
                .filter(value -> value.getCode().equals(code))
                .findAny()
                .orElseGet(() -> null);
    }


    @Override
    public String toString() {
        return this.code;
    }
}
