package com.ads.main.enums.cache;

import lombok.Getter;

@Getter
public enum CacheType {

    DASHBOARD_STATISTICS("dashboard-statistics", 60 * 10, 1000),     //

    ;
    private final String name;
    private final int expiredAfterWriter;
    private final int maximumSize;

    CacheType(String name, int expiredAfterWriter, int maximumSize) {
        this.name = name;
        this.expiredAfterWriter = expiredAfterWriter;
        this.maximumSize = maximumSize;
    }
}
