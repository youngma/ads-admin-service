package com.ads.main.core.enums.campaign;

import com.ads.main.core.vo.CodeVo;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Getter
public enum CampaignType {


    All("ALL", "전체", 1, false, new String[] { "QUIZ01", "QUIZ02" }),
    Quiz01("QUIZ01", "정답형 퀴즈(비율)", 1, true, new String[] { "QUIZ01" }),
    Quiz02("QUIZ02", "정답형 퀴즈(고정)", 2, true, new String[] { "QUIZ02" }),

    ;


    private final String code;
    private final String name;
    private final int order;
    private final boolean visible;
    private final List<String> types;


    CampaignType(String code, String name, int order, boolean visible, String[] types) {
        this.code = code;
        this.name = name;
        this.order = order;
        this.visible = visible;
        this.types = Arrays.stream(types).toList();
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
                .filter(CampaignType::isVisible)
                .map(t -> new CodeVo(t.getCode(), t.getName()))
                .toList();
    }

    public List<CampaignType> getTypes() {
        return this.types.stream().map(CampaignType::of).toList();
    }
}
