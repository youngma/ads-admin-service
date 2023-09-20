package com.ads.main.core.enums.campaign;

import com.ads.main.core.vo.CodeVo;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Getter
public enum CampaignStatus {

    Enable("ENABLE", "노출", 1),
    Disable("DISABLE", "비노출", 2),

    Request("REQUEST", "승인 요청", 1),
    Approval("APPROVAL", "승인", 2),
    Hold("HOLD", "보류", 3),
    Reject("REJECT", "거절", 4),

    ;


    private final String code;
    private final String name;
    private final int order;


    CampaignStatus(String code, String name, int order) {
        this.code = code;
        this.name = name;
        this.order = order;
    }


    public static CampaignStatus of(final String code) {
        return Arrays.stream(CampaignStatus.values())
                .filter(value -> value.getCode().equals(code))
                .findAny()
                .orElseGet(() -> null);
    }

    public static List<CodeVo> getCodes() {
        return Arrays.stream(CampaignStatus.values())
                .sorted(Comparator.comparing(CampaignStatus::getOrder))
                .map(t -> new CodeVo(t.getCode(), t.getName()))
                .toList();
    }
}
