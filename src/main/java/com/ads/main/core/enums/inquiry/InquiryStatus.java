package com.ads.main.core.enums.inquiry;

import lombok.Getter;

import java.util.Arrays;


@Getter
public enum InquiryStatus {

    Request("REQUEST", "문의 요청", 1),
    Answer("ANSWER", "답변 완료", 2),
    All("ALL", "전체", 2),

    ;

    private final String code;
    private final String name;
    private final int order;

    InquiryStatus(String code, String name, int order) {
        this.code = code;
        this.name = name;
        this.order= order;
    }


    public static InquiryStatus of(final String code) {
        return Arrays.stream(InquiryStatus.values())
                .filter(value -> value.getCode().equals(code))
                .findAny()
                .orElseGet(() -> null);
    }

    public String getStringValue() {
        return code;
    }
}
