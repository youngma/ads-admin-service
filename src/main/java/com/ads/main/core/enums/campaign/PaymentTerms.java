package com.ads.main.core.enums.campaign;

import com.ads.main.core.vo.CodeVo;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Getter
public enum PaymentTerms {

    Time("TIME", "시간", 1),
    Scroll("SCROLL", "스크롤", 2),

    ;

    private final String code;
    private final String name;
    private final int order;


    PaymentTerms(String code, String name, int order) {
        this.code = code;
        this.name = name;
        this.order= order;

    }

    public static PaymentTerms of(final String code) {
        return Arrays.stream(PaymentTerms.values())
                .filter(value -> value.getCode().equals(code))
                .findAny()
                .orElseGet(() -> null);
    }

    public static List<CodeVo> getCodes() {
        return Arrays.stream(PaymentTerms.values())
                .sorted(Comparator.comparing(PaymentTerms::getOrder))
                .map(t -> new CodeVo(t.getCode(), t.getName()))
                .toList();
    }
}
