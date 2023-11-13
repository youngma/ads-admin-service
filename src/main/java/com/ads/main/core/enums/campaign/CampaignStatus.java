package com.ads.main.core.enums.campaign;

import com.ads.main.core.vo.CodeVo;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Getter
public enum CampaignStatus {

    Exposure("EXPOSURE", "노출", 1, false),
    NonExposure("NON-EXPOSURE", "비노출", 2, false),

    Request("REQUEST", "승인 요청", 1, true),
    Approval("APPROVAL", "승인", 2, true),
    Hold("HOLD", "보류", 3, true),
    Reject("REJECT", "거절", 4, true),

    ;


    private final String code;
    private final String name;
    private final int order;
    private final boolean visible;


    CampaignStatus(String code, String name, int order, boolean visible) {
        this.code = code;
        this.name = name;
        this.order = order;
        this.visible = visible;
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
                .filter(CampaignStatus::isVisible)
                .map(t -> new CodeVo(t.getCode(), t.getName()))
                .toList();
    }
}
