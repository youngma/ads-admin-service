package com.ads.main.core.enums.user;

import com.ads.main.core.vo.CodeVo;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Getter
public enum UserStatus {

    Enable("ENABLED", "활성", 1),
    Disable("DISABLED", "비활성", 2),

    ;

    private final String code;
    private final String name;
    private final int order;

    UserStatus(String code, String name, int order) {
        this.code = code;
        this.name = name;
        this.order= order;
    }

    public static UserStatus of(final String code) {
        return Arrays.stream(UserStatus.values())
                .filter(value -> value.getCode().equals(code))
                .findAny()
                .orElseGet(() -> null);
    }

    public static List<CodeVo> getCodes() {
        return Arrays.stream(UserStatus.values())
                .sorted(Comparator.comparing(UserStatus::getOrder))
                .map(t -> new CodeVo(t.getCode(), t.getName()))
                .toList();
    }
}
