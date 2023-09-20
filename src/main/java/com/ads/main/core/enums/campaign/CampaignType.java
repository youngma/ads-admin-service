package com.ads.main.core.enums.campaign;

import com.ads.main.core.vo.CodeVo;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Getter
public enum CampaignType {

    Quiz01("QUIZ01", "정답형 퀴즈", 1),
    Type1("TYPE1", "캠페인 타입 1", 2),
    Type2("TYPE2", "캠페인 타입 2", 3),

    ;


    private final String code;
    private final String name;
    private final int order;


    CampaignType(String code, String name, int order) {
        this.code = code;
        this.name = name;
        this.order= order;

    }


    public static CampaignType of(final String code) {
        return Arrays.stream(CampaignType.values())
                .filter(value -> value.getCode().equals(code))
                .findAny()
                .orElseGet(() -> null);
    }


    public static List<CodeVo> getCodes() {
        return Arrays.stream(CampaignType.values())
                .sorted(Comparator.comparing(CampaignType::getOrder))
                .map(t -> new CodeVo(t.getCode(), t.getName()))
                .toList();
    }
}
