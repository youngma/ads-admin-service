package com.ads.main.core.security.config.dto;

import com.ads.main.core.enums.campaign.CampaignStatus;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {

    ADMIN("admin"),
    PARTNER("partner"),
    ADVERTISER("advertiser"),
    USER("user"),
    X_CODE("xcode"),

    ;
    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public static Role findRole(String roleName) {
        return Arrays.stream(Role.values())
                .filter(value -> value.getRoleName().equals(roleName))
                .findAny()
                .orElseGet(() -> null);
    }
}
