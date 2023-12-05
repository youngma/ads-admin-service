package com.ads.main.core.enums.common;


import com.ads.main.core.vo.CodeVo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Getter
@Slf4j
public enum SinkSchedule {

    X_CODE_DAILY("X_CODE_DAILY"),
    ADMIN_DAILY("ADMIN_DAILY"),
    PARTNER_DAILY("PARTNER_DAILY"),
    ADVERTISER_DAILY("ADVERTISER_DAILY"),
    QUIZ_DAILY("QUIZ_DAILY"),
    AD_RAW("AD_RAW"),

    ;


    private final String name;

    SinkSchedule(String name) {
        this.name = name;
    }

}
