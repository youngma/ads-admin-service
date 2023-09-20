package com.ads.main.core.enums.advertiser;

import com.ads.main.core.vo.CodeVo;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Getter
public enum AdGroupStatus {

    Request("REQUEST", "승인 요청", 1),
    Approval("APPROVAL", "승인", 2),
    Hold("HOLD", "보류", 3),
    Reject("REJECT", "거절", 4),


    // 승인 요청 -> 승인
    // 승인 요청 -> 보류 -> 승인
    // 승인 요청 -> 거절 -> 승인 요청 -> 승인

    ;


    private final String code;
    private final String name;
    private final int order;


    AdGroupStatus(String code, String name, int order) {
        this.code = code;
        this.name = name;
        this.order = order;
    }


    public static AdGroupStatus of(final String code) {
        return Arrays.stream(AdGroupStatus.values())
                .filter(value -> value.getCode().equals(code))
                .findAny()
                .orElseGet(() -> null);
    }

    public static List<CodeVo> getCodes() {
        return Arrays.stream(AdGroupStatus.values())
                .sorted(Comparator.comparing(AdGroupStatus::getOrder))
                .map(t -> new CodeVo(t.getCode(), t.getName()))
                .toList();
    }
}
